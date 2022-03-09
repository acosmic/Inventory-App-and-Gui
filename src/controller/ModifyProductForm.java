package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductForm implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyProductForm initialized!");

    }

    public void ModifyProductSaveBtn(ActionEvent actionEvent) {
    }

    public void ModifyProductCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }

    public void ModifyProductAddBtn(ActionEvent actionEvent) {
    }

    public void ModifyProductRemoveAssociatedPartBtn(ActionEvent actionEvent) {
    }
}
