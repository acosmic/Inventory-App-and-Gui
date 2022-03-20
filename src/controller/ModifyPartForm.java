package controller;

import com.sun.javafx.webkit.theme.RenderThemeImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public static void passInHouse(InHouse part){
        ObservableList<Part> allParts = Inventory.getAllParts();
        selectedIHPart = part;
        index = allParts.indexOf(part);
    }
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

    public void InHouseRadioBtn(ActionEvent actionEvent) {
        MPInHouseRadio.setSelected(true);
        MPOutsourcedRadio.setSelected(false);
        MPMachineIDLabel.setText("Machine ID");
    }

    public void OutsourcedRadioBtn(ActionEvent actionEvent) {
        MPInHouseRadio.setSelected(false);
        MPOutsourcedRadio.setSelected(true);
        MPMachineIDLabel.setText("Company Name");
    }

    public void ModifyPartSaveBtn(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(modPartID.getText());
        String name = modPartName.getText();
        double price = Double.parseDouble(modPartPrice.getText());
        int stock = Integer.parseInt(modPartInv.getText());
        int min = Integer.parseInt(modPartMin.getText());
        int max = Integer.parseInt(modPartMax.getText());
        if (MPInHouseRadio.isSelected() == true){
            int machineId = Integer.parseInt(modPartMachineID.getText());
            InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.updatePart(index, newPart);
        } else {
            String companyID = modPartMachineID.getText();
            Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyID);
            Inventory.updatePart(index, newPart);
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene MainForm = new Scene(root);
        stage.setTitle("C482 - Main Screen");
        stage.setScene(MainForm);
        stage.show();
        selectedOSPart = null;
        selectedIHPart = null;
    }

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

