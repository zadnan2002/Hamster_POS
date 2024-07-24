package Sale;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesManagement {
    private final List<Sale> sales = new ArrayList<>();

    public void recordSale(int productId, int quantitySold, Date saleDate, double totalAmount) {
        sales.add(new Sale(productId, quantitySold, saleDate, totalAmount));

    }


    public void generateDailySalesReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        double totalRevenue = 0;
        int totalSales = 0;

        for (Sale sale : sales) {
            String saleDate = sdf.format(sale.getSaleDate());
            if (today.equals(saleDate)) {
                totalRevenue += sale.getTotalAmount();
                totalSales += sale.getQuantitySold();
            }
        }

        System.out.println("Sales Report for: " + today);
        System.out.println("Total Products sold: " + totalSales);
        System.out.println("Total Revenue: $" + totalRevenue);
    }

    //filter sales report by date range: if we had a database entry of the sales we would have retrieved those
    //sales based on criteria and ran the generate report above but since I am using a list of sales for POC I did this method
    //to filter the sales based on date range
    public void generateSalesReport(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        double totalRevenue = 0;
        int totalSales = 0;

        for (Sale sale : sales) {
            Date saleDate = sale.getSaleDate();
            if (saleDate.after(startDate) && saleDate.before(endDate)) {
                totalRevenue += sale.getTotalAmount();
                totalSales += sale.getQuantitySold();
            }
        }

        System.out.println("Sales Report from " + sdf.format(startDate) + " to " + sdf.format(endDate));
        System.out.println("Total Products sold: " + totalSales);
        System.out.println("Total Revenue: $" + totalRevenue);
    }
}
