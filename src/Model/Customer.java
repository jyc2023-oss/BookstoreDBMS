package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty customerID;
    private SimpleStringProperty name;
    private SimpleStringProperty gender;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty address;
    private SimpleStringProperty username;
    // private SimpleStringProperty registrationDate;

    // 构造函数：不包含 CustomerID，用于“添加顾客”页面
    public Customer(String name, String gender, String phone, String email, String address, String username) {
        this.customerID = null; // 添加顾客时，CustomerID 为空
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.username = new SimpleStringProperty(username);
    }

    // 构造函数：包含 CustomerID，用于“查看顾客”页面
    public Customer(int customerID, String name, String gender, String phone, String email, String address,
            String username) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.username = new SimpleStringProperty(username);
    }

    // 属性方法（Property）
    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty genderProperty() {
        return gender;
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
    public int getCustomerID() {
        return customerID == null ? -1 : customerID.get(); // 如果 CustomerID 为空，返回 -1 表示无效
    }

    public String getName() {
        return name.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getPhone() {
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
