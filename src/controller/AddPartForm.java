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


public class AddPartForm implements Initializable {
    public Label MachineIDLabel;
    public RadioButton InHouseRadio;
    public RadioButton OutsourcedRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddPartForm initialized!");
        InHouseRadio.setSelected(true);
        OutsourcedRadio.setSelected(false);

    }

    public void InHouseRadioBtn(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
        InHouseRadio.setSelected(true);
        OutsourcedRadio.setSelected(false);
    }

    public void OutSourcedRadioBtn(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");
        InHouseRadio.setSelected(false);
        OutsourcedRadio.setSelected(true);
    }

    public void AddPartSaveBtn(ActionEvent actionEvent) {

    }

    public void AddPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
    }


}
