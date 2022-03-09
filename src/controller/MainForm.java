package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Observable;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class MainForm implements Initializable {
    //PARTS TABLE
    public TableView partsTable;
    public TableColumn partsPartId;
    public TableColumn partsPartName;
    public TableColumn partsInv;
    public TableColumn partsPrice;
    public TextField partsSearchTF;
    //PRODUCTS TABLE
    public TableView productsTable;
    public TableColumn productsProductId;
    public TableColumn productsProductName;
    public TableColumn productsInv;
    public TableColumn productsPrice;
    public TextField productsSearchTF;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("MainForm initialized!");



        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        partsPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    // Main Screen ADD Parts Button - Switch to AddPartForm
    public void MainPartsAddAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPartForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene AddPart = new Scene(root);
        stage.setTitle("C482 - Add Part Screen");
        stage.setScene(AddPart);
        stage.show();
    }

    // Main Screen MODIFY Parts Button - Switch to ModifyPartForm
    public void MainPartsModifyAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPartForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene ModifyPart = new Scene(root);
        stage.setTitle("C482 - Modify Part Screen");
        stage.setScene(ModifyPart);
        stage.show();
    }

    // Main Screen DELETE Parts Button
    public void MainPartsDeleteAction(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> allParts = Inventory.getAllParts();
        allParts.remove(selectedPart);
        partsTable.setItems(allParts);



    }

    // Main Screen ADD Products Button - Switch to Add Product Screen
    public void MainProductsAddAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProductForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene AddProduct = new Scene(root);
        stage.setTitle("C482 - Add Product Screen");
        stage.setScene(AddProduct);
        stage.show();
    }

    // Main Screen MODIFY Products Button - Switch to Modify Products Screen
    public void MainProductsModifyAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProductForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene AddProduct = new Scene(root);
        stage.setTitle("C482 - Modify Product Screen");
        stage.setScene(AddProduct);
        stage.show();
    }

    // Main Screen DELETE Products Button
    public void MainProductsDeleteAction(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        ObservableList<Product> allProducts= Inventory.getAllProducts();
        allProducts.remove(selectedProduct);
        productsTable.setItems(allProducts);
    }

    //Main Screen EXIT Button - Closes program
    public void MainScreenExit(ActionEvent actionEvent) {
        exit();
    }

    public void partsSearch(ActionEvent actionEvent) {
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
        partsTable.setItems(parts);
    }

    public void productsSearch(ActionEvent actionEvent) {
        String q = productsSearchTF.getText();
        ObservableList<Product> products = Inventory.lookupProduct(q);

        if (products.size() == 0){
            try {
                int id = Integer.parseInt(q);
                Product p = Inventory.lookupProduct(id);
                if (p != null) {
                    products.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }
        productsTable.setItems(products);
    }
}
