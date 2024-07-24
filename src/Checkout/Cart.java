package Checkout;

import Product.Product;
import Product.ProductManager;
import Sale.SalesManagement;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private  ProductManager productManager;
    private  SalesManagement salesManagement;

    public Cart(ProductManager productManager, SalesManagement salesManagement) {
        this.productManager = productManager;
        this.salesManagement = salesManagement;
    }

    private final Map<Integer, Product> currentCart = new HashMap<>();

    public void addProduct(int productId, int quantity) throws IllegalArgumentException {
        Product product = productManager.getProduct(productId);
        if (product == null) {
            System.out.println("Product with id " + productId + " not found.");
            return;
        }
        addProduct(product, quantity);
    }

    public void removeProduct(int productId) {
        Product product = productManager.getProduct(productId);
        if (product == null) {
            System.out.println("Product with id " + productId + " not found.");
            return;
        }
        if (currentCart.containsKey(productId)) {
            Product removed = currentCart.remove(productId);
            int newQuantity = removed.getQuantity() + product.getQuantity();
            productManager.updateProduct(product.getId(), product.getName(), product.getPrice(), newQuantity);
        } else {
            System.out.println("Product with id " + productId + " not found in cart.");
        }
    }

    public void viewCart() {
        System.out.println("Cart:");
        for (Map.Entry<Integer, Product> entry : currentCart.entrySet()) {
            System.out.println(entry.getValue().getName() + " - " + entry.getValue().getPrice() + " USD - Quantity: " + entry.getValue().getQuantity());
        }
        System.out.println("End of Cart");
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Map.Entry<Integer, Product> entry : currentCart.entrySet()) {
            total += entry.getValue().getPrice() * entry.getValue().getQuantity();
        }
        return total;
    }


    public double calculateTotalPriceWithDiscount(double discountThreshold, double discountRate) {
        double total = calculateTotalPrice();
        if (total > discountThreshold) {
            return total * (1 - discountRate);
        }
        return total;
    }

    public void checkout() {
        Date checkoutDate = new Date();
        double total = calculateTotalPrice();
        System.out.println("Total price: " + total + " USD");
        System.out.println("Thank you for shopping with us!");
        for (Map.Entry<Integer, Product> entry : currentCart.entrySet()) {
            recordSales(entry, checkoutDate);
        }
        currentCart.clear();
    }

    public void checkoutWithDiscount(double discountThreshold, double discountRate) {
        Date checkoutDate = new Date();
        double total = calculateTotalPriceWithDiscount(discountThreshold, discountRate);
        System.out.println("Total price with discount: " + total + " USD");
        System.out.println("Thank you for shopping with us!");
        for (Map.Entry<Integer, Product> entry : currentCart.entrySet()) {
            recordSalesWithDiscount(discountRate, entry, checkoutDate);
        }
        currentCart.clear();
    }

    private void recordSales(Map.Entry<Integer, Product> entry, Date checkoutDate) {
        double totalAmount = entry.getValue().getPrice() * entry.getValue().getQuantity();
        salesManagement.recordSale(entry.getValue().getId(), entry.getValue().getQuantity(), checkoutDate, totalAmount);
    }


    private void recordSalesWithDiscount(double discountRate, Map.Entry<Integer, Product> entry, Date checkoutDate) {
        double totalAmount = entry.getValue().getPrice() * entry.getValue().getQuantity() * (1 - discountRate);
        salesManagement.recordSale(entry.getValue().getId(), entry.getValue().getQuantity(), checkoutDate, totalAmount);
    }

    private void addProduct(Product product, int quantity) throws IllegalArgumentException {
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Items not available in desired quantity");
        }
        if (currentCart.containsKey(product.getId())) {
            updateCartProduct(product, quantity);
        } else {
            currentCart.put(product.getId(), product);
        }
        int newQuantity = product.getQuantity() - quantity;
        productManager.updateProduct(product.getId(), product.getName(), product.getPrice(), newQuantity);
    }


    private void updateCartProduct(Product product, int quantity) {
        Product cartProduct = currentCart.get(product.getId());
        int newQuantity = cartProduct.getQuantity() + quantity;
        cartProduct.setQuantity(newQuantity);
        currentCart.put(product.getId(), cartProduct);
    }
}