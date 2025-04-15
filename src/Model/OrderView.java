package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderView {
    private SimpleIntegerProperty orderId;
    private SimpleStringProperty customerUsername;
    private SimpleIntegerProperty bookId;
    private SimpleStringProperty bookTitle;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty totalAmount;
    private SimpleStringProperty orderDate;

    // 管理员查询
    public OrderView(int orderId, String customerUsername, int bookId, String bookTitle,
            int quantity, double totalAmount, String orderDate) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.customerUsername = new SimpleStringProperty(customerUsername);
        this.bookId = new SimpleIntegerProperty(bookId);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.orderDate = new SimpleStringProperty(orderDate);
    }

    // 顾客查询个人订单
    public OrderView(int orderId, int bookId, String bookTitle,
            int quantity, double totalAmount, String orderDate) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.bookId = new SimpleIntegerProperty(bookId);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.orderDate = new SimpleStringProperty(orderDate);
    }

    // 属性方法（Property）
    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public SimpleStringProperty customerUsernameProperty() {
        return customerUsername;
    }

    public SimpleIntegerProperty bookIdProperty() {
        return bookId;
    }

    public SimpleStringProperty bookTitleProperty() {
        return bookTitle;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleDoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
    }

    // Getters
    public int getOrderId() {
        return orderId.get();
    }

    public String getCustomerUsername() {
        return customerUsername.get();
    }

    public int getBookId() {
        return bookId.get();
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public String getOrderDate() {
        return orderDate.get();
    }
}
