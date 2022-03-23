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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The Add Product Class. Methods to control the add product screen.
 * */
public class AddProductForm implements Initializable {
    public TextField addProductId;
    public TextField addProductName;
    public TextField addProductInv;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;
    public TableView partsTB;
    public TableColumn partsPartIdCol;
    public TableColumn partsPartNameCol;
    public TableColumn partsInvCol;
    public TableColumn partsPriceCol;
    public TextField partsSearchTF;
    public TableColumn assocPartIdCol;
    public TableColumn assocPartNameCol;
    public TableColumn assocInvCol;
    public TableColumn assocPriceCol;
    public TableView assocPartsTB;
    public ObservableList<Part> apAssociatedParts = FXCollections.observableArrayList();
    public Label searchResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddProductForm initialized!");

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

    }

    /** Save button adds product to products list.
     * @param actionEvent Save Button
     * */
    public void AddProductSaveBtn(ActionEvent actionEvent) throws IOException {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        String name = addProductName.getText();
        double price = Double.parseDouble(addProductPrice.getText());
        int stock = Integer.parseInt(addProductInv.getText());
        int min = Integer.parseInt(addProductMin.getText());
        int max = Integer.parseInt(addProductMax.getText());

        int incrementId = 2;
        for(int i = 0; i < allProducts.size(); i++){
            Product p = allProducts.get(i);
            if(p.getId() == incrementId){
                incrementId = incrementId + 2;
            }
        }
        System.out.println(incrementId);

        int id = incrementId;
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
            Product newProduct = new Product(id,name,price,stock,min,max);
            allProducts.add(newProduct);
            newProduct.addAssociatedPart(apAssociatedParts);
            toMainScreen(actionEvent);
        }



    }

    /** Sends user to main screen.
     * @param actionEvent Cancel Button
     * */
    public void AddProductCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }

    /** Adds a part to a product's associated parts.
     * @param actionEvent Add Button
     * */
    public void AddProductAddBtn(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTB.getSelectionModel().getSelectedItem();
        if(apAssociatedParts.contains(selectedPart) != true){
            apAssociatedParts.add(selectedPart);
            assocPartsTB.setItems(apAssociatedParts);
        }
    }

    /** Removes a part from a product's associated parts.
     * @param actionEvent Remove Button
     * */
    public void AddProductRemoveAssociatedPartBtn(ActionEvent actionEvent) {
        Part selectedPart = (Part) assocPartsTB.getSelectionModel().getSelectedItem();
        apAssociatedParts.remove(selectedPart);

        Alert removeSuccess = new Alert(Alert.AlertType.CONFIRMATION);
        removeSuccess.setTitle("Removal Successful");
        removeSuccess.setContentText("Part removed from associated parts list!");
        removeSuccess.showAndWait();
    }

    /** Sends user to main screen.
     * @param actionEvent Cancel Button
     * */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }

    /** Search field to search for parts.
     * @param actionEvent Search Box
     * */
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
