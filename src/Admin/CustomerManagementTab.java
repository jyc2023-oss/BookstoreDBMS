package Admin;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import Model.Customer;
import Service.CustomerService;
import Util.*;

public class CustomerManagementTab {

    @SuppressWarnings({ "deprecation", "unchecked" })
    public VBox createContent() {
        // 主布局
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(20));

        // 筛选条件区域
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));

        // 筛选条件输入框
        TextField nameField = new TextField();
        nameField.setPromptText("姓名");
        nameField.setPrefWidth(140);

        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll("男", "女", "不限");
        genderField.setValue("不限"); // 默认值
        genderField.setPrefWidth(90);

        TextField addressField = new TextField();
        addressField.setPromptText("地址");
        addressField.setPrefWidth(250);

        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");
        usernameField.setPrefWidth(130);

        // 查询按钮
        Button queryButton = new Button("查询");
        queryButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        queryButton.setDefaultButton(true);

        HBox.setHgrow(nameField, Priority.ALWAYS);
        HBox.setHgrow(genderField, Priority.ALWAYS);
        HBox.setHgrow(addressField, Priority.ALWAYS);
        HBox.setHgrow(usernameField, Priority.ALWAYS);
        HBox.setHgrow(queryButton, Priority.NEVER); // 查询按钮宽度固定，可以不设置Hgrow

        filterBox.getChildren().addAll(
                new Label("姓名:"), nameField,
                new Label("性别:"), genderField,
                new Label("地址:"), addressField,
                new Label("用户名:"), usernameField,
                queryButton);

        // 操作按钮区域
        HBox actionBox = new HBox(20);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setPadding(new Insets(10));

        // 创建按钮
        Button addCustomerButton = new Button("添加顾客");
        Button updateCustomerButton = new Button("更新顾客");
        Button deleteCustomerButton = new Button("删除顾客");

        addCustomerButton.setPrefWidth(120);
        updateCustomerButton.setPrefWidth(120);
        deleteCustomerButton.setPrefWidth(120);

        // 将按钮添加到操作区域
        actionBox.getChildren().addAll(
                addCustomerButton,
                updateCustomerButton,
                deleteCustomerButton);

        // 查询结果表格
        TableView<Customer> tableView = new TableView<>();

        // 定义表格列
        TableColumn<Customer, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().customerIDProperty().asObject());

        TableColumn<Customer, String> nameColumn = new TableColumn<>("姓名");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Customer, String> genderColumn = new TableColumn<>("性别");
        genderColumn.setCellValueFactory(data -> data.getValue().genderProperty());

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("电话");
        phoneColumn.setCellValueFactory(data -> data.getValue().phoneProperty());

        TableColumn<Customer, String> emailColumn = new TableColumn<>("邮箱");
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Customer, String> addressColumn = new TableColumn<>("地址");
        addressColumn.setCellValueFactory(data -> data.getValue().addressProperty());

        TableColumn<Customer, String> usernameColumn = new TableColumn<>("用户名");
        usernameColumn.setCellValueFactory(data -> data.getValue().usernameProperty());

        // TableColumn<Customer, String> registrationDateColumn = new
        // TableColumn<>("注册日期");
        // registrationDateColumn.setCellValueFactory(data ->
        // data.getValue().registrationDateProperty());

        // 添加列到 TableView
        tableView.getColumns().addAll(
                idColumn, nameColumn, genderColumn, phoneColumn, emailColumn,
                addressColumn, usernameColumn);

        // 设置表格的自动列宽调整策略
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 设置列宽
        idColumn.setPrefWidth(30);
        nameColumn.setPrefWidth(90);
        genderColumn.setPrefWidth(40);
        phoneColumn.setPrefWidth(100);
        emailColumn.setPrefWidth(170);
        addressColumn.setPrefWidth(220);
        usernameColumn.setPrefWidth(80);
        // 设置表格大小
        VBox.setVgrow(tableView, Priority.ALWAYS); // 让表格在 VBox 中占据尽可能多的垂直空间
        tableView.setMaxWidth(Double.MAX_VALUE); // 允许表格最大宽度扩展
        VBox.setMargin(tableView, new Insets(0, 0, 12, 0)); // 仅为表格设置底部外边距

        // 查询按钮事件
        queryButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String gender = genderField.getValue().equals("不限") ? "" : genderField.getValue();
            String address = addressField.getText().trim();
            String username = usernameField.getText().trim();

            // 调用 Service 查询顾客
            List<Customer> customers = CustomerService.queryCustomers(name, gender, address, username);

            // 显示查询结果
            tableView.getItems().clear();
            tableView.getItems().addAll(customers);
        });

        // 操作按钮事件
        addCustomerButton.setOnAction(e -> showAddCustomerDialog());
        updateCustomerButton.setOnAction(e -> showUpdateCustomerDialog());
        deleteCustomerButton.setOnAction(e -> showDeleteCustomerDialog());

        // 将控件加入主布局
        mainBox.getChildren().addAll(filterBox, actionBox, tableView);

        return mainBox;
    }

    // 显示“添加顾客”对话框
    private void showAddCustomerDialog() {
        Stage addCustomerStage = new Stage();
        addCustomerStage.setTitle("管理员 - 添加顾客");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label nameLabel = new Label("姓名:");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入姓名");

        Label genderLabel = new Label("性别:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll("男", "女");
        genderField.setPromptText("请选择性别");

        Label emailLabel = new Label("邮箱:");
        TextField emailField = new TextField();
        emailField.setPromptText("请输入邮箱");

        Label phoneLabel = new Label("联系电话:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("请输入联系电话");

        Label addressLabel = new Label("收货地址:");
        TextField addressField = new TextField();
        addressField.setPromptText("请输入收货地址");

        Label usernameLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("请输入用户名");

        Label passwordLabel = new Label("密码:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("请输入密码");

        // 提交按钮和结果提示
        Button addButton = new Button("提交");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 将控件添加到网格布局
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(genderLabel, 0, 1);
        gridPane.add(genderField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(phoneLabel, 0, 3);
        gridPane.add(phoneField, 1, 3);
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(usernameLabel, 0, 5);
        gridPane.add(usernameField, 1, 5);
        gridPane.add(passwordLabel, 0, 6);
        gridPane.add(passwordField, 1, 6);

        // 提交按钮和结果文本布局
        VBox buttonBox = new VBox(10, addButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        // 主布局
        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        nameField.setOnAction(e -> genderField.requestFocus());
        genderField.setOnAction(e -> emailField.requestFocus());
        emailField.setOnAction(e -> phoneField.requestFocus());
        phoneField.setOnAction(e -> addressField.requestFocus());
        addressField.setOnAction(e -> usernameField.requestFocus());
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> addButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件处理
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String gender = genderField.getValue();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // 基本验证：检查输入是否为空
            if (name.isEmpty() || gender == null || email.isEmpty() || phone.isEmpty() ||
                    address.isEmpty() || username.isEmpty() || password.isEmpty()) {
                resultLabel.setText("请填写所有字段！");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // 调用 Service 添加顾客
            if (CustomerService.addCustomer(name, gender, email, phone, address, username, password)) {
                resultLabel.setText("顾客添加成功！");
                resultLabel.setStyle("-fx-text-fill: green;");
            } else {
                resultLabel.setText("顾客添加失败，请检查输入！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 450, 450);
        addCustomerStage.setScene(scene);
        addCustomerStage.show();
    }

    // 显示“更新顾客”对话框
    private void showUpdateCustomerDialog() {
        Stage updateBookStage = new Stage();
        updateBookStage.setTitle("管理员 - 更新顾客信息");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label idLabel = new Label("顾客ID:");
        TextField idField = new TextField();
        idField.setPromptText("请输入顾客ID");
        idField.setDisable(false); // 顾客ID是必填项

        Label nameLabel = new Label("姓名:");
        TextField nameField = new TextField();
        nameField.setPromptText("新姓名");

        Label genderLabel = new Label("性别:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll("男", "女");
        genderField.setPromptText("选择性别");

        Label phoneLabel = new Label("电话:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("新电话");

        Label emailLabel = new Label("邮箱:");
        TextField emailField = new TextField();
        emailField.setPromptText("新邮箱");

        Label addressLabel = new Label("地址:");
        TextField addressField = new TextField();
        addressField.setPromptText("新地址");

        Label usernameLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("新用户名");

        // 提交按钮和结果文本
        Button updateButton = new Button("更新");
        updateButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 将控件添加到网格布局
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(genderLabel, 0, 2);
        gridPane.add(genderField, 1, 2);
        gridPane.add(phoneLabel, 0, 3);
        gridPane.add(phoneField, 1, 3);
        gridPane.add(emailLabel, 0, 4);
        gridPane.add(emailField, 1, 4);
        gridPane.add(addressLabel, 0, 5);
        gridPane.add(addressField, 1, 5);
        gridPane.add(usernameLabel, 0, 6);
        gridPane.add(usernameField, 1, 6);

        // 提交按钮和结果文本布局
        VBox buttonBox = new VBox(10, updateButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        // 主布局
        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        idField.setOnAction(e -> nameField.requestFocus());
        nameField.setOnAction(e -> genderField.requestFocus());
        genderField.setOnAction(e -> phoneField.requestFocus());
        phoneField.setOnAction(e -> emailField.requestFocus());
        emailField.setOnAction(e -> addressField.requestFocus());
        addressField.setOnAction(e -> usernameField.requestFocus());
        usernameField.setOnAction(e -> updateButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件处理
        updateButton.setOnAction(e -> {
            try {
                int customerId = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String gender = genderField.getValue();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String address = addressField.getText().trim();
                String username = usernameField.getText().trim();

                // 调用 Service 更新方法
                String result = CustomerService.updateCustomer(
                        customerId, name, gender, phone, email, address, username);

                resultLabel.setText(result);
                resultLabel.setStyle(result.contains("成功") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
            } catch (NumberFormatException ex) {
                resultLabel.setText("顾客ID必须是有效整数！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 450, 450);
        updateBookStage.setScene(scene);
        updateBookStage.show();
    }

    // 显示“删除顾客”对话框
    private void showDeleteCustomerDialog() {
        Stage deleteCustomerStage = new Stage();
        deleteCustomerStage.setTitle("管理员 - 删除顾客");

        // 主布局
        VBox mainBox = new VBox(20); // 垂直布局
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        // 创建提示标签和输入框
        Label instructionLabel = new Label("请输入顾客 ID 删除：");
        instructionLabel.setStyle("-fx-font-size: 16px;");

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("顾客 ID");
        customerIdField.setPrefWidth(150);

        // 将标签和输入框放入 HBox 水平布局
        HBox inputBox = new HBox(10, instructionLabel, customerIdField);
        inputBox.setAlignment(Pos.CENTER);

        // 创建删除按钮
        Button deleteButton = new Button("删除");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        deleteButton.setDefaultButton(true); // 设置为默认按钮

        // 创建结果提示标签
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // 删除按钮事件
        deleteButton.setOnAction(e -> {
            String customerIdInput = customerIdField.getText();

            try {
                int customerId = Integer.parseInt(customerIdInput); // 转换为整数

                // 使用 DialogUtil 显示确认弹窗
                DialogUtil.showConfirmationDialog(
                        "确认删除", // 弹窗标题
                        "确定要删除顾客 ID " + customerId + " 吗？", // 弹窗提示信息
                        () -> { // 确认回调
                            String result = CustomerService.deleteCustomer(customerId); // 调用 Service 方法
                            resultLabel.setText(result); // 显示操作结果
                            resultLabel
                                    .setStyle(result.contains("成功") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                        },
                        null // 取消回调（这里可选，不需要处理）
                );

            } catch (NumberFormatException ex) {
                resultLabel.setText("顾客 ID 必须是整数！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 将所有控件添加到主布局
        mainBox.getChildren().addAll(inputBox, deleteButton, resultLabel);

        // 场景和窗口
        Scene scene = new Scene(mainBox, 400, 200);
        deleteCustomerStage.setScene(scene);
        deleteCustomerStage.show();
    }
}