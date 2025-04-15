package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty supplierID;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty address;
    private SimpleStringProperty username;

    // 构造函数：不包含 SupplierID，用于“添加供应商”页面
    public Supplier(String name, String email, String phone, String address, String username) {
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.username = new SimpleStringProperty(username);
    }

    // 构造函数（包含 SupplierID，用于查看和查询）
    public Supplier(int supplierID, String name, String phone, String email, String address, String username) {
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.username = new SimpleStringProperty(username);
    }

    // 属性方法（Property）
    public SimpleIntegerProperty supplierIDProperty() {
        return supplierID;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    // Getters
    public int getSupplierID() {
        return supplierID.get();
    }

    public String getName() {
        return name.get();
    }

    public String getphone() {
        return phone.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getUsername() {
        return username.get();
    }
}
