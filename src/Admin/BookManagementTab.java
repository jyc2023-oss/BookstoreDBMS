package Admin;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Util.*;
import Service.*;
import DAO.*;
import Model.Book;

public class BookManagementTab {
    // “书籍管理”页面内容
    public StackPane createContent() {
        // 创建四个按钮
        Button addBookButton = new Button("添加书籍 +");
        Button updateBookButton = new Button("修改书籍 🖉");
        Button deleteBookButton = new Button("删除书籍 ×");
        Button viewBooksButton = new Button("查看书籍 🔍");

        // 设置按钮基础样式
        String baseStyle = "-fx-font-size: 27px; -fx-padding: 90 145; -fx-font-weight: bold; -fx-text-fill: black; "
                + "-fx-background-radius: 10px; ";

        // 设置按钮初始样式
        addBookButton.setStyle(baseStyle + "-fx-background-color: #FF4C4C;"); // 红色
        updateBookButton.setStyle(baseStyle + "-fx-background-color: #4CAF50;"); // 绿色
        deleteBookButton.setStyle(baseStyle + "-fx-background-color: #2196F3;"); // 蓝色
        viewBooksButton.setStyle(baseStyle + "-fx-background-color: #FFC107;"); // 黄色

        // 设置按钮悬停样式（保持圆角矩形）
        addBookButton.setOnMouseEntered(e -> addBookButton.setStyle(baseStyle + "-fx-background-color: #FF6666;"));
        addBookButton.setOnMouseExited(e -> addBookButton.setStyle(baseStyle + "-fx-background-color: #FF4C4C;"));

        updateBookButton
                .setOnMouseEntered(e -> updateBookButton.setStyle(baseStyle + "-fx-background-color: #66BB6A;"));
        updateBookButton.setOnMouseExited(e -> updateBookButton.setStyle(baseStyle + "-fx-background-color: #4CAF50;"));

        deleteBookButton
                .setOnMouseEntered(e -> deleteBookButton.setStyle(baseStyle + "-fx-background-color: #42A5F5;"));
        deleteBookButton.setOnMouseExited(e -> deleteBookButton.setStyle(baseStyle + "-fx-background-color: #2196F3;"));

        viewBooksButton.setOnMouseEntered(e -> viewBooksButton.setStyle(baseStyle + "-fx-background-color: #FFD54F;"));
        viewBooksButton.setOnMouseExited(e -> viewBooksButton.setStyle(baseStyle + "-fx-background-color: #FFC107;"));

        // 创建 AnchorPane 布局
        AnchorPane anchorPane = new AnchorPane();

        // 将按钮添加到 AnchorPane
        anchorPane.getChildren().addAll(
                addBookButton,
                updateBookButton,
                deleteBookButton,
                viewBooksButton);

        AnchorPane.setTopAnchor(addBookButton, 50.0); // 左上角
        AnchorPane.setLeftAnchor(addBookButton, 50.0);

        AnchorPane.setTopAnchor(updateBookButton, 50.0); // 右上角
        AnchorPane.setRightAnchor(updateBookButton, 50.0);

        AnchorPane.setBottomAnchor(deleteBookButton, 50.0); // 左下角
        AnchorPane.setLeftAnchor(deleteBookButton, 50.0);

        AnchorPane.setBottomAnchor(viewBooksButton, 50.0); // 右下角
        AnchorPane.setRightAnchor(viewBooksButton, 50.0);

        // 按钮宽度和高度绑定到窗口大小的比例
        addBookButton.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.444));
        addBookButton.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.4));

        updateBookButton.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.444));
        updateBookButton.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.4));

        deleteBookButton.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.444));
        deleteBookButton.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.4));

        viewBooksButton.prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.444));
        viewBooksButton.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.4));

        // 按钮点击事件
        addBookButton.setOnAction(e -> showAddBookDialog());
        updateBookButton.setOnAction(e -> showUpdateBookDialog());
        deleteBookButton.setOnAction(e -> showDeleteBookDialog());
        viewBooksButton.setOnAction(e -> showViewBooksDialog());

        // 将 AnchorPane 包装到 StackPane
        StackPane stackPane = new StackPane(anchorPane);
        stackPane.setPadding(new Insets(10));
        return stackPane;
    }

    // 显示“添加书籍”对话框
    private void showAddBookDialog() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("管理员 - 添加书籍");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label titleLabel = new Label("书名:");
        TextField titleField = new TextField();
        titleField.setPromptText("书名");

        Label authorLabel = new Label("作者:");
        TextField authorField = new TextField();
        authorField.setPromptText("作者");

        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        Label priceLabel = new Label("价格:");
        TextField priceField = new TextField();
        priceField.setPromptText("价格 (￥)");

        Label categoryLabel = new Label("分类:");
        TextField categoryField = new TextField();
        categoryField.setPromptText("分类");

        Label stockQuantityLable = new Label("库存数量:");
        TextField stockQuantityField = new TextField();
        stockQuantityField.setPromptText("库存数量");

        Label reorderLevelLabel = new Label("预警水平:");
        TextField reorderLevelField = new TextField();
        reorderLevelField.setPromptText("预警水平");

        // 提交按钮和结果文本
        Button addButton = new Button("提交");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 将控件添加到网格布局
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(authorLabel, 0, 1);
        gridPane.add(authorField, 1, 1);
        gridPane.add(isbnLabel, 0, 2);
        gridPane.add(isbnField, 1, 2);
        gridPane.add(priceLabel, 0, 3);
        gridPane.add(priceField, 1, 3);
        gridPane.add(categoryLabel, 0, 4);
        gridPane.add(categoryField, 1, 4);
        gridPane.add(stockQuantityLable, 0, 5);
        gridPane.add(stockQuantityField, 1, 5);
        gridPane.add(reorderLevelLabel, 0, 6);
        gridPane.add(reorderLevelField, 1, 6);

        // 提交按钮和结果文本布局
        VBox buttonBox = new VBox(10, addButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        // 主布局
        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        titleField.setOnAction(e -> authorField.requestFocus());
        authorField.setOnAction(e -> isbnField.requestFocus());
        isbnField.setOnAction(e -> priceField.requestFocus());
        priceField.setOnAction(e -> categoryField.requestFocus());
        categoryField.setOnAction(e -> stockQuantityField.requestFocus());
        stockQuantityField.setOnAction(e -> reorderLevelField.requestFocus());
        reorderLevelField.setOnAction(e -> addButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件处理
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String price = priceField.getText();
            String category = categoryField.getText();
            String stock = stockQuantityField.getText().trim();
            String reorderLevel = reorderLevelField.getText().trim();

            // 基本验证：检查输入是否为空
            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || price.isEmpty() || category.isEmpty()
                    || stock.isEmpty() || reorderLevel.isEmpty()) {
                resultLabel.setText("请填写所有字段！");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            try {
                // 验证价格、库存数量和预警水平是否为数字
                double priceValue = Double.parseDouble(price);
                int stockValue = Integer.parseInt(stock);
                int reorderLevelValue = Integer.parseInt(reorderLevel);

                // 调用数据库操作方法
                if (BookDAO.addBook(title, author, isbn, priceValue, category, stockValue, reorderLevelValue)) {
                    resultLabel.setText("书籍添加成功！");
                    resultLabel.setStyle("-fx-text-fill: green;");
                } else {
                    resultLabel.setText("书籍添加失败，请检查输入！");
                    resultLabel.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("价格、库存数量和预警水平必须是有效数字！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 450, 450);
        addBookStage.setScene(scene);
        addBookStage.show();
    }

    // 显示“更新书籍”对话框
    private void showUpdateBookDialog() {
        Stage updateBookStage = new Stage();
        updateBookStage.setTitle("管理员 - 更新书籍");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label bookIdLabel = new Label("书籍 ID:");
        TextField bookIdField = new TextField();
        bookIdField.setPromptText("书籍 ID");

        Label titleLabel = new Label("书名:");
        TextField titleField = new TextField();
        titleField.setPromptText("书名");

        Label authorLabel = new Label("作者:");
        TextField authorField = new TextField();
        authorField.setPromptText("作者");

        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        Label priceLabel = new Label("价格:");
        TextField priceField = new TextField();
        priceField.setPromptText("价格 (￥)");

        Label categoryLabel = new Label("分类:");
        TextField categoryField = new TextField();
        categoryField.setPromptText("分类");

        Label stockQuantityLabel = new Label("库存:");
        TextField stockQuantityField = new TextField();
        stockQuantityField.setPromptText("库存数量");

        Label reorderLevelLabel = new Label("预警水平:");
        TextField reorderLevelField = new TextField();
        reorderLevelField.setPromptText("预警水平");

        // 提交按钮和结果文本
        Button updateButton = new Button("更新");
        updateButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 将控件添加到网格布局
        gridPane.add(bookIdLabel, 0, 0);
        gridPane.add(bookIdField, 1, 0);
        gridPane.add(titleLabel, 0, 1);
        gridPane.add(titleField, 1, 1);
        gridPane.add(authorLabel, 0, 2);
        gridPane.add(authorField, 1, 2);
        gridPane.add(isbnLabel, 0, 3);
        gridPane.add(isbnField, 1, 3);
        gridPane.add(priceLabel, 0, 4);
        gridPane.add(priceField, 1, 4);
        gridPane.add(categoryLabel, 0, 5);
        gridPane.add(categoryField, 1, 5);
        gridPane.add(stockQuantityLabel, 0, 6);
        gridPane.add(stockQuantityField, 1, 6);
        gridPane.add(reorderLevelLabel, 0, 7);
        gridPane.add(reorderLevelField, 1, 7);

        // 提交按钮和结果文本布局
        VBox buttonBox = new VBox(10, updateButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        // 主布局
        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        bookIdField.setOnAction(e -> titleField.requestFocus());
        titleField.setOnAction(e -> authorField.requestFocus());
        authorField.setOnAction(e -> isbnField.requestFocus());
        isbnField.setOnAction(e -> priceField.requestFocus());
        priceField.setOnAction(e -> categoryField.requestFocus());
        categoryField.setOnAction(e -> stockQuantityField.requestFocus());
        stockQuantityField.setOnAction(e -> reorderLevelField.requestFocus());
        reorderLevelField.setOnAction(e -> updateButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件处理
        updateButton.setOnAction(e -> {
            try {
                int bookID = Integer.parseInt(bookIdField.getText().trim()); // BookID 必须填写
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String isbn = isbnField.getText().trim();
                String price = priceField.getText().trim();
                String category = categoryField.getText().trim();
                String stockQuantity = stockQuantityField.getText().trim();
                String reorderLevel = reorderLevelField.getText().trim();

                String result = BookService.updateBook(bookID, title, author, isbn, price, category, stockQuantity,
                        reorderLevel);
                resultLabel.setText(result);
                resultLabel.setStyle(result.contains("成功") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
            } catch (NumberFormatException ex) {
                resultLabel.setText("书籍 ID 必须为数字！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 创建场景并显示
        Scene scene = new Scene(mainBox, 450, 500);
        updateBookStage.setScene(scene);
        updateBookStage.show();
    }

    // 显示“删除书籍”对话框
    private void showDeleteBookDialog() {
        Stage deleteBookStage = new Stage();
        deleteBookStage.setTitle("管理员 - 删除书籍");

        // 主布局
        VBox mainBox = new VBox(20); // 垂直布局
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        // 创建提示标签和输入框水平布局
        Label instructionLabel = new Label("请输入书籍 ID 删除：");
        instructionLabel.setStyle("-fx-font-size: 16px;");

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("书籍 ID");
        bookIdField.setPrefWidth(150);

        // 将标签和输入框放入 HBox 水平布局
        HBox inputBox = new HBox(10, instructionLabel, bookIdField);
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
            String bookIdInput = bookIdField.getText();

            try {
                int bookId = Integer.parseInt(bookIdInput); // 转换为整数

                // 使用 DialogUtil 显示确认弹窗
                DialogUtil.showConfirmationDialog(
                        "确认删除", // 弹窗标题
                        "确定要删除书籍 ID " + bookId + " 吗？", // 弹窗提示信息
                        () -> { // 确认回调
                            String result = BookService.deleteBook(bookId); // 调用 Service 方法
                            resultLabel.setText(result); // 显示操作结果
                            resultLabel
                                    .setStyle(result.contains("成功") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                        },
                        null // 取消回调（这里可选，不需要处理）
                );

            } catch (NumberFormatException ex) {
                resultLabel.setText("书籍 ID 必须是整数！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 将所有控件添加到主布局
        mainBox.getChildren().addAll(inputBox, deleteButton, resultLabel);

        // 创建场景并显示窗口
        Scene scene = new Scene(mainBox, 400, 200);
        deleteBookStage.setScene(scene);
        deleteBookStage.show();
    }

    // 显示“查询书籍”对话框
    @SuppressWarnings({ "unchecked", "deprecation" })
    private void showViewBooksDialog() {
        Stage viewBooksStage = new Stage();
        viewBooksStage.setTitle("管理员 - 查看书籍");

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
        tableView.setRowFactory(tv -> new TableRow<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (book == null || empty) {
                    setStyle(""); // 默认样式
                } else if (book.getStockQuantity() < book.getReorderLevel()) {
                    setStyle("-fx-background-color:rgb(238, 192, 192);"); // 红色背景，表示库存不足
                } else {
                    setStyle(""); // 恢复默认样式
                }
            }
        });

        // 定义表格列
        TableColumn<Book, Number> bookIDColumn = new TableColumn<>("ID");
        bookIDColumn.setCellValueFactory(data -> data.getValue().bookIDProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("书名");
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("作者");
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(data -> data.getValue().isbnProperty());

        TableColumn<Book, Double> priceColumn = new TableColumn<>("价格 (￥)");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        TableColumn<Book, String> categoryColumn = new TableColumn<>("分类");
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<Book, Integer> stockQuantityColumn = new TableColumn<>("库存");
        stockQuantityColumn.setCellValueFactory(data -> data.getValue().stockQuantityProperty().asObject());

        // 自定义单元格
        stockQuantityColumn.setCellFactory(col -> new TableCell<Book, Integer>() {
            @Override
            protected void updateItem(Integer stock, boolean empty) {
                super.updateItem(stock, empty);
                if (empty || stock == null) {
                    setText(null); // 空单元格
                    setStyle("");
                } else {
                    setText(stock.toString());
                    if (stock < getTableRow().getItem().getReorderLevel()) {
                        setText(stock + " !"); // 添加警告标志
                        // setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // 红色加粗
                        getTableRow().setStyle(
                                "-fx-background-color: #FFE5E5; -fx-border-color: #D32F2F; -fx-border-width: 2px; -fx-text-fill: #000000; -fx-font-weight: bold;");
                    } else {
                        setStyle(""); // 恢复默认样式
                    }
                }
            }
        });

        TableColumn<Book, Integer> reorderLevelColumn = new TableColumn<>("预警水平");
        reorderLevelColumn.setCellValueFactory(data -> data.getValue().reorderLevelProperty().asObject());

        tableView.getColumns().addAll(bookIDColumn, titleColumn, authorColumn, isbnColumn, priceColumn, categoryColumn,
                stockQuantityColumn, reorderLevelColumn);

        // 设置表格列宽
        bookIDColumn.setPrefWidth(30); // 设置BookID列宽
        titleColumn.setPrefWidth(180); // 设置书名列的最小宽度
        authorColumn.setMinWidth(150); // 设置作者列的最小宽度
        isbnColumn.setMinWidth(150); // 设置 ISBN 列的最小宽度
        priceColumn.setMinWidth(100); // 设置价格列的最小宽度
        categoryColumn.setMinWidth(120); // 设置分类列的最小宽度
        stockQuantityColumn.setMinWidth(80); // 设置库存列的最小宽度
        stockQuantityColumn.setMaxWidth(100); // 设置库存列的最大宽度
        reorderLevelColumn.setMinWidth(80); // 设置库存水平列的最小宽度
        reorderLevelColumn.setMaxWidth(100); // 设置库存水平列的最大宽度
        // 设置表格的自动列宽调整策略
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

            // 设置默认价格值
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
}
