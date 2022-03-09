package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductForm implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddProductForm initialized!");
    }


    public void AddProductSaveBtn(ActionEvent actionEvent) {
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
}
