package Sale;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int productId;
    private int quantitySold;
    private Date saleDate;
    private double totalAmount;


    private static final List<Sale> sales = new ArrayList<>();

    public Sale(int productId, int quantitySold, Date saleDate, double totalAmount) {
        this.productId = productId;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

