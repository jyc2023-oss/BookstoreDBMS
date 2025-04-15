package Customer;

import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.*;
import Service.*;

public class CustomerDashboard extends Application {

    private String username; // 顾客的用户名
    private String avatarUrl; // 顾客头像的 URL

    // 空构造：用于单独测试，跳过登录
    // public CustomerDashboard() {
    // this.username = "zhangwei";
    // this.avatarUrl =
    // "https://bpic.588ku.com/original_origin_min_pic/19/10/04/a321f23dd346c61646a546ad9cc9194d.jpg!r650";
    // }

    // 构造函数：登录传参
    public CustomerDashboard(String username, String avatarUrl) {
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public void start(Stage stage) {
        // 顶部用户信息栏
        HBox userInfoBox = createUserInfoBox();

        // 主界面色块按钮
        StackPane mainPane = createMainPane();

        // 整体布局
        VBox root = new VBox(userInfoBox, mainPane);
        root.setSpacing(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        // 设置场景
        Scene scene = new Scene(root, 1000, 620);
        stage.setTitle("客户端 - 网上书店综合管理系统");
        stage.setScene(scene);
        stage.show();
    }

    // 创建顶部用户信息栏
    public HBox createUserInfoBox() {
        // 页面 Logo
        ImageView logoView = new ImageView(new Image("file:src/Image/customer_logo.png"));
        logoView.setFitWidth(720); // 设置 logo 的宽度
        logoView.setFitHeight(40); // 设置 logo 的高度，与头像高度一致
        logoView.setPreserveRatio(true); // 保持图片比例
        logoView.setSmooth(true); // 启用平滑处理

        // 用户头像
        ImageView avatarView = new ImageView(new Image(avatarUrl));
        avatarView.setFitWidth(40);
        avatarView.setFitHeight(40);

        // 用户名标签
        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // 用户信息区域（组合头像和用户名）
        HBox userInfoBox = new HBox(10, avatarView, usernameLabel);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.setPadding(new Insets(5));
        userInfoBox.setStyle("-fx-cursor: hand;"); // 鼠标样式

        // 点击事件，弹出个人信息框
        userInfoBox.setOnMouseClicked(e -> showUserInfoDialog());

        // 顶部整体区域（组合 Logo 和用户信息）
        HBox topBox = new HBox(20, logoView, userInfoBox); // 添加间距 20
        topBox.setAlignment(Pos.CENTER_RIGHT); // 整体右对齐
        topBox.setPadding(new Insets(10));
        topBox.setStyle("-fx-background-color: #f5f5f5;"); // 背景颜色

        return topBox;
    }

    // 查询个人信息
    private void showUserInfoDialog() {
        // 获取当前用户信息
        Customer customer = CustomerService.getSelfInfo(username);

        // 如果无法获取用户信息，直接显示错误提示
        if (customer == null) {
            Stage errorStage = new Stage();
            Label resultLabel = new Label("无法加载用户信息，请稍后重试！");
            resultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            VBox errorBox = new VBox(resultLabel);
            errorBox.setAlignment(Pos.CENTER);
            errorBox.setPadding(new Insets(20));
            Scene errorScene = new Scene(errorBox, 300, 100);
            errorStage.setScene(errorScene);
            errorStage.setTitle("错误");
            errorStage.show();
            return;
        }

        // 创建用户信息显示弹窗
        Stage userInfoStage = new Stage();
        userInfoStage.setTitle("用户信息");

        // 标题
        Label titleLabel = new Label("用户信息");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 用户信息展示区域
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(15);
        infoGrid.setVgap(10);
        infoGrid.setPadding(new Insets(20));
        infoGrid.setAlignment(Pos.CENTER);

        // 添加用户信息字段
        infoGrid.add(createInfoLabel("用户ID:"), 0, 0);
        infoGrid.add(createValueLabel(String.valueOf(customer.getCustomerID())), 1, 0);

        infoGrid.add(createInfoLabel("姓名:"), 0, 1);
        infoGrid.add(createValueLabel(customer.getName()), 1, 1);

        infoGrid.add(createInfoLabel("性别:"), 0, 2);
        infoGrid.add(createValueLabel(customer.getGender()), 1, 2);

        infoGrid.add(createInfoLabel("电话:"), 0, 3);
        infoGrid.add(createValueLabel(customer.getPhone()), 1, 3);

        infoGrid.add(createInfoLabel("邮箱:"), 0, 4);
        infoGrid.add(createValueLabel(customer.getEmail()), 1, 4);

        infoGrid.add(createInfoLabel("地址:"), 0, 5);
        infoGrid.add(createValueLabel(customer.getAddress()), 1, 5);

        infoGrid.add(createInfoLabel("用户名:"), 0, 6);
        infoGrid.add(createValueLabel(customer.getUsername()), 1, 6);

        // 按钮区域
        Button editButton = new Button("修改信息");
        editButton.setStyle(
                "-fx-font-size: 14px; -fx-padding: 5 15; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            userInfoStage.close();
            showEditUserInfoDialog();
        });

        Button closeButton = new Button("关闭");
        closeButton.setStyle(
                "-fx-font-size: 14px; -fx-padding: 5 15; -fx-background-color: #F44336; -fx-text-fill: white;");
        closeButton.setOnAction(e -> userInfoStage.close());

        HBox buttonBox = new HBox(20, editButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        // 主布局
        VBox mainBox = new VBox(20, titleLabel, infoGrid, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        Scene scene = new Scene(mainBox, 450, 450);
        userInfoStage.setScene(scene);
        userInfoStage.show();
    }

    // 工具方法：创建标签
    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return label;
    }

    // 工具方法：创建值标签
    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px;");
        return label;
    }

    // 修改个人信息页面
    private void showEditUserInfoDialog() {
        // 获取当前登录用户信息
        Customer customer = CustomerService.getSelfInfo(username);

        if (customer == null) {
            Label resultLabel = new Label("未能获取用户信息！");
            resultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            VBox errorBox = new VBox(resultLabel);
            errorBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(errorBox, 300, 100);
            Stage errorStage = new Stage();
            errorStage.setScene(scene);
            errorStage.setTitle("错误");
            errorStage.show();
            return;
        }

        // 创建编辑个人信息的弹窗
        Stage editInfoStage = new Stage();
        editInfoStage.setTitle("编辑个人信息");

        // 表单字段
        Label nameLabel = new Label("姓名:");
        TextField nameField = new TextField(customer.getName());

        Label genderLabel = new Label("性别:");
        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("男", "女");
        genderBox.setValue(customer.getGender());

        Label phoneLabel = new Label("电话:");
        TextField phoneField = new TextField(customer.getPhone());

        Label emailLabel = new Label("邮箱:");
        TextField emailField = new TextField(customer.getEmail());

        Label addressLabel = new Label("地址:");
        TextField addressField = new TextField(customer.getAddress());

        // 结果信息标签
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        // 保存按钮
        Button saveButton = new Button("保存");
        saveButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 15;");
        saveButton.setOnAction(e -> {
            String newName = nameField.getText().trim();
            String newGender = genderBox.getValue();
            String newPhone = phoneField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newAddress = addressField.getText().trim();

            if (newName.isEmpty() || newGender == null || newPhone.isEmpty() || newEmail.isEmpty()
                    || newAddress.isEmpty()) {
                resultLabel.setText("请填写所有字段！");
                return;
            }

            boolean success = CustomerService.updateSelfInfo(
                    customer.getCustomerID(),
                    newName,
                    newGender,
                    newPhone,
                    newEmail,
                    newAddress);

            if (success) {
                resultLabel.setText("修改成功！");
                resultLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
            } else {
                resultLabel.setText("修改失败，请稍后再试！");
                resultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });

        // 取消按钮
        Button cancelButton = new Button("取消");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 15;");
        cancelButton.setOnAction(e -> editInfoStage.close());

        // 布局
        GridPane formPane = new GridPane();
        formPane.setAlignment(Pos.CENTER);
        formPane.setHgap(10);
        formPane.setVgap(15);
        formPane.setPadding(new Insets(20));

        formPane.add(nameLabel, 0, 0);
        formPane.add(nameField, 1, 0);
        formPane.add(genderLabel, 0, 1);
        formPane.add(genderBox, 1, 1);
        formPane.add(phoneLabel, 0, 2);
        formPane.add(phoneField, 1, 2);
        formPane.add(emailLabel, 0, 3);
        formPane.add(emailField, 1, 3);
        formPane.add(addressLabel, 0, 4);
        formPane.add(addressField, 1, 4);

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20, formPane, resultLabel, buttonBox);
        mainBox.setAlignment(Pos.CENTER);

        // 设置回车键事件
        nameField.setOnAction(e -> genderBox.requestFocus());
        genderBox.setOnAction(e -> phoneField.requestFocus());
        phoneField.setOnAction(e -> emailField.requestFocus());
        emailField.setOnAction(e -> addressField.requestFocus());
        addressField.setOnAction(e -> saveButton.fire());

        Scene scene = new Scene(mainBox, 400, 400);
        editInfoStage.setScene(scene);
        editInfoStage.show();
    }

    // 创建主界面色块按钮
    private StackPane createMainPane() {
        // 按钮
        Button viewBooksButton = new Button("查看书籍 🔍");
        Button manageOrdersButton = new Button("订单管理 📦");

        viewBooksButton.setOnAction(e -> openViewBooksDialog());
        manageOrdersButton.setOnAction(e -> openOrderManagementDialog());

        // 主界面布局
        AnchorPane anchorPane = new AnchorPane(viewBooksButton, manageOrdersButton);

        // 左侧按钮定位
        AnchorPane.setLeftAnchor(viewBooksButton, 55.0);
        AnchorPane.setTopAnchor(viewBooksButton, 50.0);

        // 右侧按钮定位
        AnchorPane.setRightAnchor(manageOrdersButton, 55.0);
        AnchorPane.setTopAnchor(manageOrdersButton, 50.0);

        // 按钮样式
        String baseStyle = "-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; "
                + "-fx-background-radius: 10px; ";

        String hoverStyleGreen = baseStyle + "-fx-background-color: #45A049"; // 鼠标悬停绿色
        String hoverStyleBlue = baseStyle + "-fx-background-color: #1976D2"; // 鼠标悬停蓝色

        viewBooksButton.setStyle(baseStyle + "-fx-background-color: #4CAF50");
        manageOrdersButton.setStyle(baseStyle + "-fx-background-color: #2196F3");

        // 鼠标悬停效果
        viewBooksButton.setOnMouseEntered(e -> viewBooksButton.setStyle(hoverStyleGreen));
        viewBooksButton.setOnMouseExited(e -> viewBooksButton.setStyle(baseStyle + "-fx-background-color: #4CAF50"));

        manageOrdersButton.setOnMouseEntered(e -> manageOrdersButton.setStyle(hoverStyleBlue));
        manageOrdersButton
                .setOnMouseExited(e -> manageOrdersButton.setStyle(baseStyle + "-fx-background-color: #2196F3"));

        // 按钮大小
        viewBooksButton.setPrefSize(400, 300);
        manageOrdersButton.setPrefSize(400, 300);

        // 自动缩放
        viewBooksButton.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.43));
        // viewBooksButton.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.4));
        manageOrdersButton.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.43));
        // manageOrdersButton.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.4));

        return new StackPane(anchorPane);
    }

    // 打开查看书籍页面
    @SuppressWarnings({ "unchecked", "deprecation" })
    private void openViewBooksDialog() {
        Stage viewBooksStage = new Stage();
        viewBooksStage.setTitle("查看书籍");

        // 主布局
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(20));

        // 筛选条件区域
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));

        // 筛选条件输入框
        TextField titleField = new TextField();
        titleField.setPromptText("书名");
        titleField.setPrefWidth(140);

        TextField authorField = new TextField();
        authorField.setPromptText("作者");
        authorField.setPrefWidth(130);

        TextField categoryField = new TextField();
        categoryField.setPromptText("分类");
        categoryField.setPrefWidth(70);

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        isbnField.setPrefWidth(130);

        TextField minPriceField = new TextField();
        minPriceField.setPromptText("最低价格");
        minPriceField.setPrefWidth(64);

        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("最高价格");
        maxPriceField.setPrefWidth(64);

        // 查询按钮
        Button queryButton = new Button("查询");
        queryButton.setStyle("-fx-font-size: 14px; -fx-padding: 6 20;");
        queryButton.setDefaultButton(true);

        HBox.setHgrow(titleField, Priority.ALWAYS);
        HBox.setHgrow(authorField, Priority.ALWAYS);
        HBox.setHgrow(categoryField, Priority.ALWAYS);
        HBox.setHgrow(isbnField, Priority.ALWAYS);
        HBox.setHgrow(minPriceField, Priority.ALWAYS);
        HBox.setHgrow(maxPriceField, Priority.ALWAYS);
        HBox.setHgrow(queryButton, Priority.NEVER); // 查询按钮宽度固定，可以不设置Hgrow

        filterBox.getChildren().addAll(
                new Label("书名:"), titleField,
                new Label("作者:"), authorField,
                new Label("分类:"), categoryField,
                new Label("ISBN:"), isbnField,
                new Label("价格:"), minPriceField, new Label("~"), maxPriceField,
                queryButton);

        // 查询结果表格
        TableView<Book> tableView = new TableView<>();

        // 定义表格列
        TableColumn<Book, Integer> bookIdColumn = new TableColumn<>("书籍ID");
        bookIdColumn.setCellValueFactory(data -> data.getValue().bookIDProperty().asObject());

        TableColumn<Book, String> titleColumn = new TableColumn<>("书名");
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("作者");
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(data -> data.getValue().isbnProperty());

        TableColumn<Book, Double> priceColumn = new TableColumn<>("价格");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        TableColumn<Book, String> categoryColumn = new TableColumn<>("分类");
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<Book, String> availabilityColumn = new TableColumn<>("库存状态");
        availabilityColumn.setCellValueFactory(data -> {
            int stock = data.getValue().getStockQuantity();
            return new SimpleStringProperty(stock > 0 ? "有" : "无");
        });

        // 自定义单元格：标红无库存的行
        availabilityColumn.setCellFactory(col -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String availability, boolean empty) {
                super.updateItem(availability, empty);
                if (empty || availability == null) {
                    setText(null); // 空单元格
                    setStyle("");
                } else {
                    setText(availability);
                    if ("无".equals(availability)) {
                        setText(availability + " !");
                        getTableRow().setStyle(
                                "-fx-background-color: #FFE5E5; -fx-border-color: #D32F2F; -fx-border-width: 2px; -fx-text-fill: #000000; -fx-font-weight: bold;");
                    } else {
                        getTableRow().setStyle(""); // 恢复默认样式
                    }
                }
            }
        });

        tableView.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, isbnColumn, priceColumn, categoryColumn,
                availabilityColumn);

        // 设置列宽和表格
        bookIdColumn.setPrefWidth(40);
        titleColumn.setPrefWidth(180);
        authorColumn.setMinWidth(150);
        isbnColumn.setMinWidth(150);
        priceColumn.setMinWidth(100);
        categoryColumn.setMinWidth(120);
        availabilityColumn.setMinWidth(80);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 设置表格大小
        VBox.setVgrow(tableView, Priority.ALWAYS); // 让表格在 VBox 中占据尽可能多的垂直空间
        tableView.setMaxWidth(Double.MAX_VALUE); // 允许表格最大宽度扩展
        VBox.setMargin(tableView, new Insets(0, 0, 12, 0)); // 仅为表格设置底部外边距

        // 查询按钮事件
        queryButton.setOnAction(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            String isbn = isbnField.getText().trim();
            String minPrice = minPriceField.getText().trim();
            String maxPrice = maxPriceField.getText().trim();

            /// 设置默认价格值
            if (minPrice.isEmpty()) {
                minPrice = "0"; // 默认最低价格为 0
            }
            if (maxPrice.isEmpty()) {
                maxPrice = String.valueOf(Double.MAX_VALUE); // 默认最高价格为最大值
            }

            try {
                // 检查价格是否为有效数字
                Double.parseDouble(minPrice);
                Double.parseDouble(maxPrice);

                // 调用 Service 查询书籍
                List<Book> books = BookService.queryBooks(title, author, category, isbn, minPrice, maxPrice);

                // 显示查询结果
                tableView.getItems().clear();
                tableView.getItems().addAll(books);
            } catch (NumberFormatException ex) {
                // 如果价格输入无效，弹出提示
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("价格输入有误");
                alert.setContentText("请确保最低价格和最高价格为有效数字！");
                alert.showAndWait();
            }
        });

        // 将控件加入主布局
        mainBox.getChildren().addAll(filterBox, tableView);

        // 场景和窗口
        Scene scene = new Scene(mainBox, 1000, 620);
        viewBooksStage.setScene(scene);
        viewBooksStage.show();
    }

    // 打开订单管理页面
    @SuppressWarnings({ "unchecked", "deprecation" })
    private void openOrderManagementDialog() {
        Stage orderManagementStage = new Stage();
        orderManagementStage.setTitle("顾客 - 订单管理");

        // 主布局
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(20));

        // 筛选条件区域
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));

        // 筛选条件输入框
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("订单ID");
        orderIdField.setPrefWidth(80);

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("书籍ID");
        bookIdField.setPrefWidth(80);

        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("书名");
        bookTitleField.setPrefWidth(180);

        // 查询按钮
        Button queryButton = new Button("查询");
        queryButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        queryButton.setDefaultButton(true);

        // 添加按钮
        Button addButton = new Button("添加");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");

        HBox.setHgrow(orderIdField, Priority.ALWAYS);
        HBox.setHgrow(bookIdField, Priority.ALWAYS);
        HBox.setHgrow(bookTitleField, Priority.ALWAYS);
        HBox.setHgrow(queryButton, Priority.NEVER);
        HBox.setHgrow(addButton, Priority.NEVER);

        filterBox.getChildren().addAll(
                new Label("订单ID:"), orderIdField,
                new Label("书籍ID:"), bookIdField,
                new Label("书名:"), bookTitleField,
                queryButton,
                addButton);

        // 查询结果表格
        TableView<OrderView> tableView = new TableView<>();

        // 定义表格列
        TableColumn<OrderView, Integer> orderIdColumn = new TableColumn<>("订单ID");
        orderIdColumn.setCellValueFactory(data -> data.getValue().orderIdProperty().asObject());

        TableColumn<OrderView, String> customerUsernameColumn = new TableColumn<>("顾客用户名");
        customerUsernameColumn.setCellValueFactory(data -> data.getValue().customerUsernameProperty());

        TableColumn<OrderView, Integer> bookIdColumn = new TableColumn<>("书籍ID");
        bookIdColumn.setCellValueFactory(data -> data.getValue().bookIdProperty().asObject());

        TableColumn<OrderView, String> bookTitleColumn = new TableColumn<>("书名");
        bookTitleColumn.setCellValueFactory(data -> data.getValue().bookTitleProperty());

        TableColumn<OrderView, Integer> quantityColumn = new TableColumn<>("购买数量");
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());

        TableColumn<OrderView, Double> totalAmountColumn = new TableColumn<>("总金额");
        totalAmountColumn.setCellValueFactory(data -> data.getValue().totalAmountProperty().asObject());

        TableColumn<OrderView, String> orderDateColumn = new TableColumn<>("下单时间");
        orderDateColumn.setCellValueFactory(data -> data.getValue().orderDateProperty());

        tableView.getColumns().addAll(orderIdColumn, customerUsernameColumn, bookIdColumn, bookTitleColumn,
                quantityColumn, totalAmountColumn, orderDateColumn);
        // 设置表格的自动列宽调整策略
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 设置列宽
        orderIdColumn.setPrefWidth(30);
        customerUsernameColumn.setPrefWidth(80);
        bookIdColumn.setPrefWidth(30);
        bookTitleColumn.setPrefWidth(100);
        quantityColumn.setPrefWidth(50);
        totalAmountColumn.setPrefWidth(50);
        orderDateColumn.setPrefWidth(100);
        // 设置表格大小
        VBox.setVgrow(tableView, Priority.ALWAYS);
        tableView.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(tableView, new Insets(0, 0, 12, 0));

        // 查询按钮事件
        queryButton.setOnAction(e -> {
            String orderId = orderIdField.getText().trim();
            String bookId = bookIdField.getText().trim();
            String bookTitle = bookTitleField.getText().trim();

            // 调用服务层查询订单
            List<OrderView> orders = OrderService.queryOrders(orderId, username, bookId, bookTitle);

            // 显示查询结果
            tableView.getItems().clear();
            tableView.getItems().addAll(orders);
        });

        // 添加按钮事件
        addButton.setOnAction(e -> showAddOrderDialog());

        // 将组件添加到主布局
        mainBox.getChildren().addAll(filterBox, tableView);

        Scene scene = new Scene(mainBox, 1000, 620);
        orderManagementStage.setScene(scene);
        orderManagementStage.show();
    }

    // 添加订单功能
    private void showAddOrderDialog() {
        Stage addOrderStage = new Stage();
        addOrderStage.setTitle("顾客 - 添加订单");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 表单字段
        Label bookIdLabel = new Label("书籍ID:");
        TextField bookIdField = new TextField();
        bookIdField.setPromptText("请输入书籍ID");

        Label quantityLabel = new Label("数量:");
        TextField quantityField = new TextField();
        quantityField.setPromptText("请输入购买数量");

        // 提交按钮和结果标签
        Button addButton = new Button("提交");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 布局
        gridPane.add(bookIdLabel, 0, 0);
        gridPane.add(bookIdField, 1, 0);
        gridPane.add(quantityLabel, 0, 1);
        gridPane.add(quantityField, 1, 1);

        VBox mainBox = new VBox(20, gridPane, addButton, resultLabel);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        bookIdField.setOnAction(e -> quantityField.requestFocus());
        quantityField.setOnAction(e -> addButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件
        addButton.setOnAction(e -> {
            String bookIdInput = bookIdField.getText().trim();
            String quantityInput = quantityField.getText().trim();

            try {
                int bookId = Integer.parseInt(bookIdInput);
                int quantity = Integer.parseInt(quantityInput);

                // 调用服务层方法处理下单逻辑
                boolean success = OrderService.placeOrder(username, bookId, quantity);
                if (success) {
                    resultLabel.setText("订单提交成功！");
                    resultLabel.setStyle("-fx-text-fill: green;");
                } else {
                    resultLabel.setText("订单提交失败，请检查库存或输入！");
                    resultLabel.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("请输入有效的数字！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 400, 300);
        addOrderStage.setScene(scene);
        addOrderStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
