package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductForm implements Initializable {
    public TableView partsTB;
    public TableColumn partsPartIdCol;
    public TableColumn partsPartNameCol;
    public TableColumn partsInvCol;
    public TableColumn partsPriceCol;

    public TextField partsSearchTF;

    public TableView assocPartsTB;
    public TableColumn assocPartIdCol;
    public TableColumn assocPartNameCol;
    public TableColumn assocInvCol;
    public TableColumn assocPriceCol;

    public TextField prodIdTF;
    public TextField prodNameTF;
    public TextField prodInvTF;
    public TextField prodPriceTF;
    public TextField prodMaxTF;
    public TextField prodMinTF;

    private static Product selectedProduct = null;
    private static int index = 0;

    public ObservableList<Part> mpAssociatedParts = FXCollections.observableArrayList();

    public static void passProduct(Product product){
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        selectedProduct = product;
        index = allProducts.indexOf(product);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Modify Product
        System.out.println("ModifyProductForm initialized!");
        prodIdTF.setText(String.valueOf(selectedProduct.getId()));
        prodNameTF.setText(selectedProduct.getName());
        prodInvTF.setText(String.valueOf(selectedProduct.getStock()));
        prodPriceTF.setText(String.valueOf(selectedProduct.getPrice()));
        prodMaxTF.setText(String.valueOf(selectedProduct.getMax()));
        prodMinTF.setText(String.valueOf(selectedProduct.getMin()));

        // Parts Table
        partsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTB.setItems(Inventory.getAllParts());

        // Associated Parts Table
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartsTB.setItems(selectedProduct.getAllAssociatedParts());

    }

    public void ModifyProductSaveBtn(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(prodIdTF.getText());
        String name = prodNameTF.getText();
        int stock = Integer.parseInt(prodInvTF.getText());
        double price = Double.parseDouble(prodPriceTF.getText());
        int min = Integer.parseInt(prodMinTF.getText());
        int max = Integer.parseInt(prodMaxTF.getText());

        Inventory.updateProduct(index, new Product(id, name, price, stock, min, max));

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }

    public void ModifyProductCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }
    // Add Part to associated parts list
    public void ModifyProductAddBtn(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTB.getSelectionModel().getSelectedItem();
        if (mpAssociatedParts.contains(selectedPart) != true &&
                Inventory.getAllProducts().get(index).getAllAssociatedParts().contains(selectedPart) !=true){
            mpAssociatedParts.add(selectedPart);
        } else {}
        Inventory.getAllProducts().get(index).addAssociatedPart(mpAssociatedParts);
        mpAssociatedParts.clear();
    }

    // Remove a part from the associated parts list
    public void ModifyProductRemoveAssociatedPartBtn(ActionEvent actionEvent) {
        Part selectedPart = (Part) assocPartsTB.getSelectionModel().getSelectedItem();
        Inventory.getAllProducts().get(index).deleteAssociatedPart(selectedPart);
    }

    // Search Parts List for a part
    public void partsSearchAction(ActionEvent actionEvent) {
        String q = partsSearchTF.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);


        if (parts.size() == 0 ){
            try {
                int id = Integer.parseInt(q);
                Part p = Inventory.lookupPart(id);
                if (p != null) {
                    parts.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }
        partsTB.setItems(parts);
    }
}
