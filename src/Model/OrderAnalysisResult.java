package Model;

import java.util.List;

public class OrderAnalysisResult {
    private int totalOrders;
    private double totalSales;
    private List<BookSales> topSellingBooks;
    private List<CustomerSales> customerSalesList;

    // Getters and Setters
    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public List<BookSales> getTopSellingBooks() {
        return topSellingBooks;
    }

    public void setTopSellingBooks(List<BookSales> topSellingBooks) {
        this.topSellingBooks = topSellingBooks;
    }

    public List<CustomerSales> getCustomerSalesList() {
        return customerSalesList;
    }

    public void setCustomerSalesList(List<CustomerSales> customerSalesList) {
        this.customerSalesList = customerSalesList;
    }
}