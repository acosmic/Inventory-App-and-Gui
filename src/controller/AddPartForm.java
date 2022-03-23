package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Add Part Form Class. Methods for controlling the add part screen.
 * */
public class AddPartForm implements Initializable {
    public Label MachineIDLabel;
    public RadioButton InHouseRadio;
    public RadioButton OutsourcedRadio;
    public TextField addPartId;
    public TextField addPartName;
    public TextField addPartInv;
    public TextField addPartPrice;
    public TextField addPartMax;
    public TextField addPartMachineId;
    public TextField addPartMin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddPartForm initialized!");
        InHouseRadio.setSelected(true);
        OutsourcedRadio.setSelected(false);

    }

    /**
     * @param actionEvent InHouse radio button is selected
     * */
    public void InHouseRadioBtn(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
        InHouseRadio.setSelected(true);
        OutsourcedRadio.setSelected(false);
    }

    /**
     * @param actionEvent Outsourced radio button is selected
     * */
    public void OutSourcedRadioBtn(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");
        InHouseRadio.setSelected(false);
        OutsourcedRadio.setSelected(true);
    }

    /** Save button adds part to parts list.
     * @param actionEvent Save Button
     * */
    public void AddPartSaveBtn(ActionEvent actionEvent) throws IOException {
        ObservableList<Part> allParts = Inventory.getAllParts();
        String name = addPartName.getText();
        double price = Double.parseDouble(addPartPrice.getText());
        int stock = Integer.parseInt(addPartInv.getText());
        int min = Integer.parseInt(addPartMin.getText());
        int max = Integer.parseInt(addPartMax.getText());

        int incrementId = 1;
        for(int i = 0; i < allParts.size(); i++){
            Part p = allParts.get(i);
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
            try{
                if (InHouseRadio.isSelected()){
                    int machineId = Integer.parseInt(addPartMachineId.getText());
                    InHouse newPart = new InHouse(id,name,price,stock,min,max,machineId);
                    allParts.add(newPart);
                }
                else if (OutsourcedRadio.isSelected()) {
                    String companyName = addPartMachineId.getText();
                    Outsourced newPart = new Outsourced(id,name,price,stock,min,max,companyName);
                    allParts.add(newPart);
                }
                toMainScreen(actionEvent);
            }
            catch (Exception e) {
                Alert invalidInput = new Alert(Alert.AlertType.ERROR);
                invalidInput.setTitle("Invalid Input");
                invalidInput.setContentText("Input is not valid, please make sure Inv,Max,Min and Machine ID are integers");
                invalidInput.showAndWait();
            }
        }




    }

    /** Calls toMainScreen method and sends user to main screen.
     * @param actionEvent Cancel Button
     * */
    public void AddPartCancelBtn(ActionEvent actionEvent) throws IOException {
        toMainScreen(actionEvent);
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


}
