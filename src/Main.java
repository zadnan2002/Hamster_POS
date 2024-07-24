import Auth.UserManagement;
import Checkout.Cart;
import Product.Product;
import Product.ProductManager;
import Sale.SalesManagement;

import javax.naming.AuthenticationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static SalesManagement sm = new SalesManagement();
    private static Scanner scanner = new Scanner(System.in);
    private static UserManagement um = new UserManagement();
    private static ProductManager pm = new ProductManager(um);

    public static void main(String[] args) throws AuthenticationException {
        um.registerUser("admin", "admin");
        um.login("admin", "admin");
        initializeItems(pm);
        um.logout();
        authentication();
    }

    private static void initializeItems(ProductManager pm) {
        pm.addProduct(new Product(1, "Keyboard", 20.0, 100, 10));
        pm.addProduct(new Product(2, "Mouse", 10.0, 50, 5));
        pm.addProduct(new Product(3, "Monitor", 100.0, 20, 2));
    }

    private static void authentication() {
        printAuthDirections();
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                userSignIn();
                break;
            case 2:
                userSignUp();

                break;
            case 3:
                userSignOut();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void printAuthDirections() {
        System.out.println("Welcome to the Inventory Management System");
        System.out.println("Please select an option:");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("3. Sign out");
        System.out.println("4. Exit");
        System.out.println("Enter your choice:");
    }

    private static void userSignIn() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        try {
            um.login(username, password);
            selectAction();
        } catch (AuthenticationException e) {
            System.out.println("Invalid username or password. Please try again.");
            userSignIn();
        }
    }

    private static void userSignOut() {
        um.logout();
        System.out.println("User signed out.");
        authentication();

    }

    private static void userSignUp() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        if (um.registerUser(username, password)) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("User already exists. Please try again.");
            userSignUp();
        }
        authentication();
    }

    private static void selectAction() {
        printActionDirections();
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                browse();
                break;
            case 2:
                addProduct();
                break;
            case 3:
                updateProduct();
                break;
            case 4:
                deleteProduct();
                break;
            case 5:
                generateSalesReport();
                break;
            case 6:
                userSignOut();
                break;
            case 7:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void printActionDirections() {
        System.out.println("Please select an option:");
        System.out.println("1. Browse products");
        System.out.println("2. Add product");
        System.out.println("3. Update product");
        System.out.println("4. Delete product");
        System.out.println("5. Generate Sales Report");
        System.out.println("6. Sign out");
        System.out.println("7. Exit");
        System.out.println("Enter your choice:");
    }

    private static void addProduct() {
        System.out.println("Enter product id:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        System.out.println("Enter product price:");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter product quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter reorder level:");
        int reorderLevel = Integer.parseInt(scanner.nextLine());
        pm.addProduct(new Product(id, name, price, quantity, reorderLevel));
        System.out.println("Product added successfully.");
        selectAction();
    }

    private static void updateProduct() {
        System.out.println("Enter product id:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        System.out.println("Enter product price:");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter product quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());
        pm.updateProduct(id, name, price, quantity);
        System.out.println("Product updated complete.");
        selectAction();
    }

    private static void deleteProduct() {
        System.out.println("Enter product id:");
        int id = Integer.parseInt(scanner.nextLine());
        pm.deleteProduct(id);
        System.out.println("Product deleted successfully.");
        selectAction();
    }

    private static void browse() {
        printBrowseDirections();

        Cart cart = new Cart(pm, sm);
        String input = scanner.nextLine();
        if (input.equals("signout")) {
            userSignOut();
        } else if (input.equals("back")) {
            selectAction();
        } else if (input.equals("viewcart")) {
            cart.viewCart();
            browse();
        } else if (input.equals("remove")) {
            System.out.println("Enter product id:");
            int id = Integer.parseInt(scanner.nextLine());
            cart.removeProduct(id);
            System.out.println("Product removed from cart.");
            browse();
        } else if (input.equals("checkout")) {
            checkout(cart);
        } else {
            try {
                int id = Integer.parseInt(input);
                System.out.println("Enter quantity:");
                int quantity = Integer.parseInt(scanner.nextLine());
                cart.addProduct(id, quantity);
                System.out.println("Do you want to purchase more items? (yes/no)");
                String purchaseMore = scanner.nextLine();
                if (purchaseMore.equals("yes")) {
                    browse();
                } else {
                    checkout(cart);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please try again.");
                browse();
            }

        }


    }

    private static void printBrowseDirections() {
        System.out.println("Welcome!");
        System.out.println("Here are the products available:");
        pm.viewProducts();
        System.out.println("If you wish to sign out at any point please type in 'signout'");
        System.out.println("If you wish to go back to the main menu please type in 'back'");
        System.out.println("If you wish to checkout please type in 'checkout'");
        System.out.println("If you wish to view your cart please type in 'viewcart'");
        System.out.println("If you wish to remove an item from your cart please type in 'remove'");
        System.out.println("If you wish to purchase a product please type in the item id:");
    }

    private static void generateSalesReport() {
        printSalesDirections();
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                sm.generateDailySalesReport();
                selectAction();
                break;
            case 2:
                generateSalesReportByDate();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                generateSalesReport();
        }
    }

    private static void printSalesDirections() {
        System.out.println("Please select an option:");
        System.out.println("1. Generate daily sales report");
        System.out.println("2. Generate sales report by date range");
    }

    private static void generateSalesReportByDate() {
        System.out.println("Enter the start date (yyyy-MM-dd):");
        String start = scanner.nextLine();
        System.out.println("Enter the end date (yyyy-MM-dd):");
        String end = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            sm.generateSalesReport(startDate, endDate);
            selectAction();
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            generateSalesReport();
        }
    }

    private static void checkout(Cart cart) {
        System.out.println("Would you like to apply a discount? (yes/no)");
        String applyDiscount = scanner.nextLine();
        if (applyDiscount.equals("yes")) {
            System.out.println("Enter discount threshold:");
            double discountThreshold = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter discount rate:");
            double discountRate = Double.parseDouble(scanner.nextLine());
            double total = cart.calculateTotalPriceWithDiscount(discountThreshold, discountRate);
            System.out.println("Total price after discount: " + total + " USD");
            System.out.println("Here is your receipt:");
            cart.checkoutWithDiscount(discountThreshold, discountRate);
        } else {
            double total = cart.calculateTotalPrice();
            System.out.println("Total price: " + total + " USD");
            System.out.println("Here is your receipt:");
            cart.checkout();
        }
        System.out.println("Would you like to add a new cart? (yes/no)");
        String addNewCart = scanner.nextLine();
        if (addNewCart.equals("yes")) {
            browse();
        } else {
            selectAction();
        }
    }


}
