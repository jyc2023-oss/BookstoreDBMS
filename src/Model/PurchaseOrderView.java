package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PurchaseOrderView {
    private SimpleIntegerProperty purchaseOrderID;
    private SimpleIntegerProperty supplierID;
    private SimpleStringProperty supplierName;
    private SimpleIntegerProperty bookID;
    private SimpleStringProperty bookTitle;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty orderDate;

    // 构造函数：有采购订单ID，用于查询采购订单
    public PurchaseOrderView(int purchaseOrderID, int supplierID, String supplierName, int bookID, String bookTitle,
            int quantity, String orderDate) {
        this.purchaseOrderID = new SimpleIntegerProperty(purchaseOrderID);
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.bookID = new SimpleIntegerProperty(bookID);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.orderDate = new SimpleStringProperty(orderDate);
    }

    // 构造函数：无采购订单ID，用于添加采购订单
    public PurchaseOrderView(int supplierID, String supplierName, int bookID, String bookTitle,
            int quantity, String orderDate) {
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.bookID = new SimpleIntegerProperty(bookID);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.orderDate = new SimpleStringProperty(orderDate);
    }

    public SimpleIntegerProperty purchaseOrderIDProperty() {
        return purchaseOrderID;
    }

    public SimpleIntegerProperty supplierIDProperty() {
        return supplierID;
    }

    public SimpleStringProperty supplierNameProperty() {
        return supplierName;
    }

    public SimpleIntegerProperty bookIDProperty() {
        return bookID;
    }

    public SimpleStringProperty bookTitleProperty() {
        return bookTitle;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
    }

    public int getPurchaseOrderID() {
        return purchaseOrderID.get();
    }

    public int getSupplierID() {
        return supplierID.get();
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public int getBookID() {
        return bookID.get();
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public String getOrderDate() {
        return orderDate.get();
    }
}
