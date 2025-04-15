package Admin;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import Model.Supplier;
import Service.SupplierService;
import Util.*;

public class SupplierManagementTab {

    @SuppressWarnings({ "unchecked", "deprecation" })
    public static VBox createContent() {
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
        nameField.setPromptText("供应商名称");
        nameField.setPrefWidth(140);

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
        HBox.setHgrow(addressField, Priority.ALWAYS);
        HBox.setHgrow(usernameField, Priority.ALWAYS);
        HBox.setHgrow(queryButton, Priority.NEVER); // 查询按钮宽度固定，可以不设置Hgrow

        // 将控件添加到筛选区域
        filterBox.getChildren().addAll(
                new Label("名称:"), nameField,
                new Label("地址:"), addressField,
                new Label("用户名:"), usernameField,
                queryButton);

        // 操作按钮区域
        HBox actionBox = new HBox(20);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setPadding(new Insets(10));

        Button addSupplierButton = new Button("添加供应商");
        Button updateSupplierButton = new Button("更新供应商");
        Button deleteSupplierButton = new Button("删除供应商");

        // 设置宽度
        addSupplierButton.setPrefWidth(140);
        updateSupplierButton.setPrefWidth(140);
        deleteSupplierButton.setPrefWidth(140);

        actionBox.getChildren().addAll(
                addSupplierButton,
                updateSupplierButton,
                deleteSupplierButton);

        // 查询结果表格
        TableView<Supplier> tableView = new TableView<>();

        // 定义表格列
        TableColumn<Supplier, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().supplierIDProperty().asObject());

        TableColumn<Supplier, String> nameColumn = new TableColumn<>("名称");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Supplier, String> phoneColumn = new TableColumn<>("联系方式");
        phoneColumn.setCellValueFactory(data -> data.getValue().phoneProperty());

        TableColumn<Supplier, String> emailColumn = new TableColumn<>("邮箱");
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Supplier, String> addressColumn = new TableColumn<>("地址");
        addressColumn.setCellValueFactory(data -> data.getValue().addressProperty());

        TableColumn<Supplier, String> usernameColumn = new TableColumn<>("用户名");
        usernameColumn.setCellValueFactory(data -> data.getValue().usernameProperty());

        // 添加列到 TableView
        tableView.getColumns().addAll(idColumn, nameColumn, phoneColumn, emailColumn, addressColumn, usernameColumn);

        // 设置表格的自动列宽调整策略
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 设置列宽
        idColumn.setPrefWidth(30);
        nameColumn.setPrefWidth(90);
        phoneColumn.setPrefWidth(100);
        emailColumn.setPrefWidth(190);
        addressColumn.setPrefWidth(200);
        usernameColumn.setPrefWidth(90);
        // 设置表格大小
        VBox.setVgrow(tableView, Priority.ALWAYS); // 让表格在 VBox 中占据尽可能多的垂直空间
        tableView.setMaxWidth(Double.MAX_VALUE); // 允许表格最大宽度扩展
        VBox.setMargin(tableView, new Insets(0, 0, 12, 0)); // 仅为表格设置底部外边距

        // 查询按钮事件
        queryButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String username = usernameField.getText().trim();

            // 调用 Service 查询供应商
            List<Supplier> suppliers = SupplierService.querySuppliers(name, address, username);

            // 显示查询结果
            tableView.getItems().clear();
            tableView.getItems().addAll(suppliers);
        });

        // 添加供应商按钮事件
        addSupplierButton.setOnAction(e -> showAddSupplierDialog());
        updateSupplierButton.setOnAction(e -> showUpdateSupplierDialog());
        deleteSupplierButton.setOnAction(e -> showDeleteSupplierDialog());

        // 将控件加入主布局
        mainBox.getChildren().addAll(filterBox, actionBox, tableView);

        return mainBox;
    }

    // 添加供应商
    private static void showAddSupplierDialog() {
        Stage addSupplierStage = new Stage();
        addSupplierStage.setTitle("管理员 - 添加供应商");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label nameLabel = new Label("供应商名称:");
        TextField nameField = new TextField();
        nameField.setPromptText("供应商名称");

        Label phoneLabel = new Label("联系方式:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("供应商联系方式");

        Label emailLabel = new Label("邮箱:");
        TextField emailField = new TextField();
        emailField.setPromptText("供应商邮箱");

        Label addressLabel = new Label("地址:");
        TextField addressField = new TextField();
        addressField.setPromptText("供应商地址");

        Label usernameLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("供应商用户名");

        Label passwordLabel = new Label("密码:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");

        // 提交按钮和结果文本
        Button addButton = new Button("提交");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 将控件添加到网格布局
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(phoneLabel, 0, 1);
        gridPane.add(phoneField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(addressLabel, 0, 3);
        gridPane.add(addressField, 1, 3);
        gridPane.add(usernameLabel, 0, 4);
        gridPane.add(usernameField, 1, 4);
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(passwordField, 1, 5);

        // 提交按钮和结果文本布局
        VBox buttonBox = new VBox(10, addButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        // 主布局
        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        nameField.setOnAction(e -> phoneField.requestFocus());
        phoneField.setOnAction(e -> emailField.requestFocus());
        emailField.setOnAction(e -> addressField.requestFocus());
        addressField.setOnAction(e -> usernameField.requestFocus());
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> addButton.fire());

        // 提交按钮事件处理
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // 验证输入
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || username.isEmpty()
                    || password.isEmpty()) {
                resultLabel.setText("请填写所有字段！");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // 调用数据库操作方法
            if (SupplierService.addSupplier(name, phone, email, address, username, password)) {
                resultLabel.setText("供应商添加成功！");
                resultLabel.setStyle("-fx-text-fill: green;");
            } else {
                resultLabel.setText("供应商添加失败，请检查输入！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 450, 450);
        addSupplierStage.setScene(scene);
        addSupplierStage.show();
    }

    // 修改供应商
    private static void showUpdateSupplierDialog() {
        Stage updateSupplierStage = new Stage();
        updateSupplierStage.setTitle("管理员 - 更新供应商信息");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label idLabel = new Label("供应商 ID:");
        TextField idField = new TextField();
        idField.setPromptText("供应商 ID");

        Label nameLabel = new Label("名称:");
        TextField nameField = new TextField();
        nameField.setPromptText("名称");

        Label phoneLabel = new Label("联系方式:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("联系方式");

        Label emailLabel = new Label("邮箱:");
        TextField emailField = new TextField();
        emailField.setPromptText("邮箱");

        Label addressLabel = new Label("地址:");
        TextField addressField = new TextField();
        addressField.setPromptText("地址");

        Label usernameLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");

        // 提交按钮和结果文本
        Button updateButton = new Button("提交");
        updateButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 将控件添加到网格布局
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(phoneLabel, 0, 2);
        gridPane.add(phoneField, 1, 2);
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(usernameLabel, 0, 5);
        gridPane.add(usernameField, 1, 5);

        // 提交按钮和结果文本布局
        VBox buttonBox = new VBox(10, updateButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        // 主布局
        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        idField.setOnAction(e -> nameField.requestFocus());
        nameField.setOnAction(e -> phoneField.requestFocus());
        phoneField.setOnAction(e -> emailField.requestFocus());
        emailField.setOnAction(e -> addressField.requestFocus());
        addressField.setOnAction(e -> usernameField.requestFocus());
        usernameField.setOnAction(e -> updateButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件处理
        updateButton.setOnAction(e -> {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String username = usernameField.getText().trim();

            // 验证输入
            if (idText.isEmpty()) {
                resultLabel.setText("供应商 ID 是必填项！");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            int supplierID;
            try {
                supplierID = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                resultLabel.setText("供应商 ID 必须是整数！");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // 调用 Service 方法
            if (SupplierService.updateSupplier(supplierID, name, phone, email, address, username)) {
                resultLabel.setText("供应商信息修改成功！");
                resultLabel.setStyle("-fx-text-fill: green;");
            } else {
                resultLabel.setText("供应商信息修改失败，请检查输入！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 450, 450);
        updateSupplierStage.setScene(scene);
        updateSupplierStage.show();
    }

    // 删除供应商
    private static void showDeleteSupplierDialog() {
        Stage deleteSupplierStage = new Stage();
        deleteSupplierStage.setTitle("管理员 - 删除供应商");

        // 主布局
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        // 输入供应商 ID
        Label instructionLabel = new Label("请输入供应商 ID 删除：");
        instructionLabel.setStyle("-fx-font-size: 16px;");

        TextField supplierIDField = new TextField();
        supplierIDField.setPromptText("供应商 ID");
        supplierIDField.setPrefWidth(150);

        // 将标签和输入框放入 HBox 水平布局
        HBox inputBox = new HBox(10, instructionLabel, supplierIDField);
        inputBox.setAlignment(Pos.CENTER);

        // 删除按钮
        Button deleteButton = new Button("删除");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        deleteButton.setDefaultButton(true); // 设置为默认按钮

        // 结果标签
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // 删除按钮事件
        deleteButton.setOnAction(e -> {
            String supplierIDInput = supplierIDField.getText().trim();

            // 验证输入是否为整数
            try {
                int supplierID = Integer.parseInt(supplierIDInput);

                // 显示确认弹窗
                DialogUtil.showConfirmationDialog(
                        "确认删除",
                        "确定要删除供应商 ID " + supplierID + " 吗？",
                        () -> { // 确认回调
                            if (SupplierService.deleteSupplier(supplierID)) {
                                resultLabel.setText("供应商删除成功！");
                                resultLabel.setStyle("-fx-text-fill: green;");
                            } else {
                                resultLabel.setText("供应商删除失败，请检查输入！");
                                resultLabel.setStyle("-fx-text-fill: red;");
                            }
                        },
                        null // 取消回调
                );

            } catch (NumberFormatException ex) {
                resultLabel.setText("供应商 ID 必须是整数！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 布局
        mainBox.getChildren().addAll(inputBox, deleteButton, resultLabel);

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 400, 200);
        deleteSupplierStage.setScene(scene);
        deleteSupplierStage.show();
    }
}