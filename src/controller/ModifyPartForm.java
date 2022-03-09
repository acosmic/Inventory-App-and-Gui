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

public class ModifyPartForm implements Initializable {

    public Label MPMachineIDLabel;
    public RadioButton MPInHouseRadio;
    public RadioButton MPOutsourcedRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyPartForm initialized!");
        MPInHouseRadio.setSelected(true);
        MPOutsourcedRadio.setSelected(false);
    }

    public void InHouseRadioBtn(ActionEvent actionEvent) {
        MPMachineIDLabel.setText("Machine ID");
        MPInHouseRadio.setSelected(true);
        MPOutsourcedRadio.setSelected(false);
    }

    public void OutsourcedRadioBtn(ActionEvent actionEvent) {
        MPMachineIDLabel.setText("Company Name");
        MPInHouseRadio.setSelected(false);
        MPOutsourcedRadio.setSelected(true);
    }

    public void ModifyPartSaveBtn(ActionEvent actionEvent) {
    }

    public void ModifyPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }
}

