package Product;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int reorderLevel;

    public Product(int id, String name, double price, int quantity, int reorderLevel) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (quantity < reorderLevel) {
            System.out.println("Product" + name + " is below reorder level. Please order more.");
        }
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }



    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
