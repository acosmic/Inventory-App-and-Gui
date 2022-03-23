package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class. Methods for manipulating parts and products.
 * */
public class Inventory  {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds part to all parts list.
     * @param Part part to add
     * */
    public static void addPart(Part Part){ allParts.add(Part); }

    /** Adds product to all products list.
     * @param Product product to add
     * */
    public static void addProduct(Product Product){ allProducts.add(Product); }

    /** Look up part by a part's ID.
     * @param partId the ID used to look up part
     * */
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

    /** Look up product by a product's ID.
     * @param productId the ID used to look up product
     * */
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

    /** Look up part by a part's name.
     * @param partName the name used to look up part
     * */
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
    /** Look up product by a product's name.
     * @param productName the ID used to look up product
     * */
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

    /** Updates a part.
     * @param selectedPart the part to update
     * */
    public static void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(selectedPart);

    }

    /** Updates a product.
     * @param selectedProduct the product to update
     * */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.remove(index);
        allProducts.add(selectedProduct);
    }

    /** Deletes a part.
     * @param selectedPart the part to delete
     * */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return false;
    }

    /** Deletes a product.
     * @param selectedProduct the product to delete
     * */
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return false;
    }

    /** Returns a list of all parts
     * @return allParts
     * */
    public static ObservableList<Part> getAllParts(){ return allParts; }

    /** Returns a list of all products
     * @return allProducts
     * */
    public static ObservableList<Product> getAllProducts(){ return allProducts; }


}

