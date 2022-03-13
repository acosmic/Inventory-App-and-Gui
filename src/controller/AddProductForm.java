package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductForm implements Initializable {
    public TextField addProductId;
    public TextField addProductName;
    public TextField addProductInv;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddProductForm initialized!");
    }


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
        Product newProduct = new Product(id,name,price,stock,min,max);
        allProducts.add(newProduct);
        toMainScreen(actionEvent);

    }

    public void AddProductCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }

    public void AddProductAddBtn(ActionEvent actionEvent) {
    }

    public void AddProductRemoveAssociatedPartBtn(ActionEvent actionEvent) {
    }

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }
}
