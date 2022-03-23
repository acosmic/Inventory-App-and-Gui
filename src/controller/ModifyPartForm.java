package controller;

import com.sun.javafx.webkit.theme.RenderThemeImpl;
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
 * The Modify Part Form. Methods for controlling the modify part screen.
 * */
public class ModifyPartForm implements Initializable {

    public Label MPMachineIDLabel;
    public RadioButton MPInHouseRadio;
    public RadioButton MPOutsourcedRadio;
    public TextField modPartID;
    public TextField modPartName;
    public TextField modPartInv;
    public TextField modPartPrice;
    public TextField modPartMax;
    public TextField modPartMachineID;
    public TextField modPartMin;

    private static InHouse selectedIHPart = null;
    private static Outsourced selectedOSPart = null;
    private static int index = 0;

    /** Pass InHouse part from main screen to modify screen.
     * @param part InHouse part to pass
     * */
    public static void passInHouse(InHouse part){
        ObservableList<Part> allParts = Inventory.getAllParts();
        selectedIHPart = part;
        index = allParts.indexOf(part);
    }

    /** Pass Outsourced part from main screen to modify screen.
     * @param part Outsourced part to pass
     * */
    public static void passOutsourced(Outsourced part){
        ObservableList<Part> allParts = Inventory.getAllParts();
        selectedOSPart = part;
        index = allParts.indexOf(part);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyPartForm initialized!");

        if(selectedIHPart != null) {
            MPInHouseRadio.setSelected(true);
            MPOutsourcedRadio.setSelected(false);
            MPMachineIDLabel.setText("Machine ID");
            modPartID.setText(String.valueOf(selectedIHPart.getId()));
            modPartName.setText(selectedIHPart.getName());
            modPartPrice.setText(String.valueOf(selectedIHPart.getPrice()));
            modPartInv.setText(String.valueOf(selectedIHPart.getStock()));
            modPartMax.setText(String.valueOf(selectedIHPart.getMax()));
            modPartMin.setText(String.valueOf(selectedIHPart.getMin()));
            modPartMachineID.setText(String.valueOf(selectedIHPart.getMachineID()));
        } else if (selectedOSPart != null) {
            MPOutsourcedRadio.setSelected(true);
            MPInHouseRadio.setSelected(false);
            MPMachineIDLabel.setText("Company Name");
            modPartID.setText(String.valueOf(selectedOSPart.getId()));
            modPartName.setText(selectedOSPart.getName());
            modPartPrice.setText(String.valueOf(selectedOSPart.getPrice()));
            modPartInv.setText(String.valueOf(selectedOSPart.getStock()));
            modPartMax.setText(String.valueOf(selectedOSPart.getMax()));
            modPartMin.setText(String.valueOf(selectedOSPart.getMin()));
            modPartMachineID.setText(selectedOSPart.getCompanyName());
        }

    }

    /**
     * @param actionEvent InHouse radio button is selected
     * */
    public void InHouseRadioBtn(ActionEvent actionEvent) {
        MPInHouseRadio.setSelected(true);
        MPOutsourcedRadio.setSelected(false);
        MPMachineIDLabel.setText("Machine ID");
    }

    /**
     * @param actionEvent Outsourced radio button is selected
     * */
    public void OutsourcedRadioBtn(ActionEvent actionEvent) {
        MPInHouseRadio.setSelected(false);
        MPOutsourcedRadio.setSelected(true);
        MPMachineIDLabel.setText("Company Name");
    }

    /** Saves modified part to the parts list.
     * @param actionEvent Save Button
     * */
    public void ModifyPartSaveBtn(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(modPartID.getText());
        String name = modPartName.getText();
        double price = Double.parseDouble(modPartPrice.getText());
        int stock = Integer.parseInt(modPartInv.getText());
        int min = Integer.parseInt(modPartMin.getText());
        int max = Integer.parseInt(modPartMax.getText());

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
            if (MPInHouseRadio.isSelected() == true) {
                int machineId = Integer.parseInt(modPartMachineID.getText());
                Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineId));
            } else {
                String companyID = modPartMachineID.getText();
                Inventory.updatePart(index, new Outsourced(id, name, price, stock, min, max, companyID));
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene MainForm = new Scene(root);
            stage.setTitle("C482 - Main Screen");
            stage.setScene(MainForm);
            stage.show();
            selectedOSPart = null;
            selectedIHPart = null;
        }
    }

    /** Sends user back to main screen.
     * @param actionEvent Cancel Button
     * */
    public void ModifyPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
        selectedOSPart = null;
        selectedIHPart = null;
    }
}

