package Product;

import Auth.User;
import Auth.UserManagement;

import java.util.HashMap;

public class ProductManager {
    //using hashmap instead of Database and where we usually fetch based on criteria only for POC
    private HashMap<Integer, Product> products;
    private User currentUser;
    private UserManagement userManagement;

    public ProductManager(UserManagement userSession) {
        this.currentUser = userSession.getCurrentUser();
        this.products = new HashMap<>();
        this.userManagement = userSession;
    }

    public void addProduct(Product product) {
        if (!userManagement.isAuthenticated()) {
            System.out.println("User not authenticated");
            return;
        }
        products.put(product.getId(), product);
        System.out.println("Product added: " + product);

    }

    public void viewProducts() {
        System.out.println("Product List:");
        for (Product product : products.values()) {
            System.out.println(product);
        }
    }

    public void updateProduct(int id, String name, double price, int quantity) {
        if (!userManagement.isAuthenticated()) {
            System.out.println("User not authenticated");
            return;
        }
        Product product = products.get(id);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            System.out.println("Product updated: " + product);
        } else {
            System.out.println("Product not found with id: " + id);
        }
    }

    public void deleteProduct(int id) {
        if (!userManagement.isAuthenticated()) {
            System.out.println("User not authenticated");
            return;
        }
        Product product = products.remove(id);
        if (product != null) {
            System.out.println("Product deleted: " + product);
        } else {
            System.out.println("Product not found with id: " + id);
        }
    }

    public void getReorderList() {
        System.out.println("Reorder List:");
        for (Product product : products.values()) {
            if (product.getQuantity() < product.getReorderLevel()) {
                System.out.println(product);
            }
        }
        System.out.println("End of Reorder List");
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public UserManagement getUserManagement() {
        return userManagement;
    }
}
