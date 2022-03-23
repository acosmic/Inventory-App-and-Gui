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
    public ObservableList<Part> transferAssociatedParts = FXCollections.observableArrayList();
    public Label searchResults;

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

        searchResults.setText("");

    }

    public void ModifyProductSaveBtn(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(prodIdTF.getText());
        String name = prodNameTF.getText();
        int stock = Integer.parseInt(prodInvTF.getText());
        double price = Double.parseDouble(prodPriceTF.getText());
        int min = Integer.parseInt(prodMinTF.getText());
        int max = Integer.parseInt(prodMaxTF.getText());

        if (max <= min){
            Alert minMaxAlert = new Alert(Alert.AlertType.ERROR);
            minMaxAlert.setTitle("Invalid Min Max Values");
            minMaxAlert.setContentText("Max value must be greater than Min value.");
            minMaxAlert.showAndWait();

        }
        else if (stock > max || stock < min){
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setTitle("Invalid Inv");
            stockAlert.setContentText("Inv value must be between Min and Max values");
            stockAlert.showAndWait();
        }
        else {
            transferAssociatedParts = selectedProduct.getAllAssociatedParts();
            Product newProduct = new Product(id, name, price, stock, min, max);
            Inventory.updateProduct(index, newProduct);
            newProduct.addAssociatedPart(transferAssociatedParts);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene MainForm = new Scene(root);
            stage.setTitle("C482 - Main Screen");
            stage.setScene(MainForm);
            stage.show();
        }


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

        if (!selectedProduct.getAllAssociatedParts().contains(selectedPart))
            if (!mpAssociatedParts.contains(selectedPart)) {
                mpAssociatedParts.add(selectedPart);
            }
        selectedProduct.addAssociatedPart(mpAssociatedParts);
        mpAssociatedParts.clear();
    }

    // Remove a part from the associated parts list
    public void ModifyProductRemoveAssociatedPartBtn(ActionEvent actionEvent) {
        Part selectedPart = (Part) assocPartsTB.getSelectionModel().getSelectedItem();
        selectedProduct.deleteAssociatedPart(selectedPart);

        Alert removeSuccess = new Alert(Alert.AlertType.CONFIRMATION);
        removeSuccess.setTitle("Removal Successful");
        removeSuccess.setContentText("Part removed from associated parts list!");
        removeSuccess.showAndWait();
    }

    // Search Parts List for a part
    public void partsSearchAction(ActionEvent actionEvent) {
        searchResults.setText("");
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
                searchResults.setText("No search results!");
            }
        }
        partsTB.setItems(parts);
    }
}
