package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory  {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static void addPart(Part Part){ allParts.add(Part);  }
    public static void addProduct(Product Product){ allProducts.add(Product); }
    public static Part lookupPart(int partId){
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(int i = 0; i < allParts.size(); i++){
            Part p = allParts.get(i);

            if (p.getId() == partId){
                return p;
            }
        }
        return null;
    }
    public static Product lookupProduct(int productId) {
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            for (int i = 0; i < allProducts.size(); i++) {
                Product p = allProducts.get(i);

                if (p.getId() == productId) {
                    return p;
                }

            }
            return null;
        }
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part p : allParts){
            if(p.getName().contains(partName)){
                foundParts.add(p);
            }
        }
        return foundParts;
    }
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product p : allProducts){
            if(p.getName().contains(productName)){
                foundProducts.add(p);
            }
        }
        return foundProducts;
    }
    public static void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(selectedPart);

    }
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.remove(index);
        allProducts.add(selectedProduct);
    }
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return false;
    }
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return false;
    }
    public static ObservableList<Part> getAllParts(){ return allParts; }
    public static ObservableList<Product> getAllProducts(){ return allProducts; }


}

