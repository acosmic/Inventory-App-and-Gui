package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        stage.setTitle("C482 - Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private static void addTestData(){
        InHouse wheel = new InHouse(1, "Wheel", 100.00, 15, 1, 50, 1);
        Inventory.addPart(wheel);
        InHouse seat = new InHouse(3, "Seat", 25.00, 30, 1, 100, 2);
        Inventory.addPart(seat);
        Outsourced tire = new Outsourced(5, "Tire", 85.00, 50, 2, 500, "Toyo");
        Inventory.addPart(tire);
        Product car = new Product(2, "Veloster", 26000, 3, 1, 5);
        Inventory.addProduct(car);
        Product bike = new Product(4, "R6", 18000, 2, 1, 10);
        Inventory.addProduct(bike);
        Product truck = new Product(6, "Silverado", 40000, 5, 1, 5);
        Inventory.addProduct(truck);
    }


    public static void main(String[] args){
        addTestData();
        launch(args);
    }
}
