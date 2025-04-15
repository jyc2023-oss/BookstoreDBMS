package Model;

public class CustomerSales {
    private String customerName;
    private double totalSpent;

    public CustomerSales(String customerName, double totalSpent) {
        this.customerName = customerName;
        this.totalSpent = totalSpent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalSpent() {
        return totalSpent;
    }
}