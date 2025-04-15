package Admin;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import Model.PurchaseOrderView;
import Service.PurchaseOrderService;

public class PurchaseOrderManagementTab {

    @SuppressWarnings({ "unchecked", "deprecation" })
    public VBox createContent() {
        // 主布局
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(20));

        // 筛选条件区域
        VBox filterBox = new VBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));

        // 第一行筛选条件
        HBox filterRow1 = new HBox(10);
        filterRow1.setAlignment(Pos.CENTER);

        TextField purchaseOrderIDField = new TextField();
        purchaseOrderIDField.setPromptText("采购订单ID");

        TextField supplierIDField = new TextField();
        supplierIDField.setPromptText("供应商ID");

        TextField bookIDField = new TextField();
        bookIDField.setPromptText("书籍ID");

        filterRow1.getChildren().addAll(
                new Label("采购订单ID:"), purchaseOrderIDField,
                new Label("供应商ID:"), supplierIDField,
                new Label("书籍ID:"), bookIDField);

        // 第二行筛选条件
        HBox filterRow2 = new HBox(10);
        filterRow2.setAlignment(Pos.CENTER);

        TextField supplierNameField = new TextField();
        supplierNameField.setPromptText("供应商名称");
        supplierNameField.setPrefWidth(270);

        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("书籍名称");
        bookTitleField.setPrefWidth(273);

        Button queryButton = new Button("查询");
        queryButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 25;");
        queryButton.setDefaultButton(true);

        Button addButton = new Button("添加");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 25;");

        filterRow2.getChildren().addAll(
                new Label("供应商名称:"), supplierNameField,
                new Label("书籍名称:"), bookTitleField,
                queryButton, addButton);

        filterBox.getChildren().addAll(filterRow1, filterRow2);

        HBox.setHgrow(purchaseOrderIDField, Priority.ALWAYS);
        HBox.setHgrow(supplierIDField, Priority.ALWAYS);
        HBox.setHgrow(bookIDField, Priority.ALWAYS);
        HBox.setHgrow(supplierNameField, Priority.ALWAYS);
        HBox.setHgrow(bookTitleField, Priority.ALWAYS);
        HBox.setHgrow(queryButton, Priority.NEVER);
        HBox.setHgrow(addButton, Priority.NEVER);

        // 查询结果表格
        TableView<PurchaseOrderView> tableView = new TableView<>();

        // 定义表格列
        TableColumn<PurchaseOrderView, Integer> purchaseOrderIDColumn = new TableColumn<>("采购订单ID");
        purchaseOrderIDColumn.setCellValueFactory(data -> data.getValue().purchaseOrderIDProperty().asObject());

        TableColumn<PurchaseOrderView, Integer> supplierIDColumn = new TableColumn<>("供应商ID");
        supplierIDColumn.setCellValueFactory(data -> data.getValue().supplierIDProperty().asObject());

        TableColumn<PurchaseOrderView, String> supplierNameColumn = new TableColumn<>("供应商名称");
        supplierNameColumn.setCellValueFactory(data -> data.getValue().supplierNameProperty());

        TableColumn<PurchaseOrderView, Integer> bookIDColumn = new TableColumn<>("书籍ID");
        bookIDColumn.setCellValueFactory(data -> data.getValue().bookIDProperty().asObject());

        TableColumn<PurchaseOrderView, String> bookTitleColumn = new TableColumn<>("书籍名称");
        bookTitleColumn.setCellValueFactory(data -> data.getValue().bookTitleProperty());

        TableColumn<PurchaseOrderView, Integer> quantityColumn = new TableColumn<>("数量");
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());

        TableColumn<PurchaseOrderView, String> orderDateColumn = new TableColumn<>("采购日期");
        orderDateColumn.setCellValueFactory(data -> data.getValue().orderDateProperty());

        tableView.getColumns().addAll(
                purchaseOrderIDColumn, supplierIDColumn, supplierNameColumn,
                bookIDColumn, bookTitleColumn, quantityColumn, orderDateColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 设置列宽
        purchaseOrderIDColumn.setPrefWidth(50);
        supplierIDColumn.setPrefWidth(45);
        supplierNameColumn.setPrefWidth(130);
        bookIDColumn.setPrefWidth(35);
        bookTitleColumn.setPrefWidth(100);
        quantityColumn.setPrefWidth(40);
        orderDateColumn.setPrefWidth(90);
        // 设置表格大小
        VBox.setVgrow(tableView, Priority.ALWAYS); // 让表格在 VBox 中占据尽可能多的垂直空间
        tableView.setMaxWidth(Double.MAX_VALUE); // 允许表格最大宽度扩展
        VBox.setMargin(tableView, new Insets(0, 0, 12, 0)); // 仅为表格设置底部外边距

        // 查询按钮事件
        queryButton.setOnAction(e -> {
            String purchaseOrderID = purchaseOrderIDField.getText().trim();
            String supplierID = supplierIDField.getText().trim();
            String bookID = bookIDField.getText().trim();
            String supplierName = supplierNameField.getText().trim();
            String bookTitle = bookTitleField.getText().trim();

            // 调用 Service 查询采购订单
            List<PurchaseOrderView> purchaseOrders = PurchaseOrderService.queryPurchaseOrders(
                    purchaseOrderID, supplierID, bookID, supplierName, bookTitle);

            // 显示查询结果
            tableView.getItems().clear();
            tableView.getItems().addAll(purchaseOrders);
        });

        // 添加按钮事件
        addButton.setOnAction(e -> showAddPurchaseOrderDialog());

        // 将控件加入主布局
        mainBox.getChildren().addAll(filterBox, tableView);

        return mainBox;
    }

    // 添加采购订单
    private void showAddPurchaseOrderDialog() {
        Stage addOrderStage = new Stage();
        addOrderStage.setTitle("管理员 - 添加采购订单");

        // 创建网格布局
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // 添加控件
        Label supplierIDLabel = new Label("供应商ID:");
        TextField supplierIDField = new TextField();
        supplierIDField.setPromptText("供应商ID");

        Label bookIDLabel = new Label("书籍ID:");
        TextField bookIDField = new TextField();
        bookIDField.setPromptText("书籍ID");

        Label quantityLabel = new Label("采购数量:");
        TextField quantityField = new TextField();
        quantityField.setPromptText("采购数量");

        // 提交按钮和结果文本
        Button addButton = new Button("提交");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // 布局控件
        gridPane.add(supplierIDLabel, 0, 0);
        gridPane.add(supplierIDField, 1, 0);
        gridPane.add(bookIDLabel, 0, 1);
        gridPane.add(bookIDField, 1, 1);
        gridPane.add(quantityLabel, 0, 2);
        gridPane.add(quantityField, 1, 2);

        VBox buttonBox = new VBox(10, addButton, resultLabel);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20, gridPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        // 设置回车键事件
        supplierIDField.setOnAction(e -> bookIDField.requestFocus());
        bookIDField.setOnAction(e -> quantityField.requestFocus());
        quantityField.setOnAction(e -> addButton.fire()); // 最后一个输入框按下回车，触发提交按钮

        // 提交按钮事件处理
        addButton.setOnAction(e -> {
            String supplierID = supplierIDField.getText().trim();
            String bookID = bookIDField.getText().trim();
            String quantity = quantityField.getText().trim();

            if (supplierID.isEmpty() || bookID.isEmpty() || quantity.isEmpty()) {
                resultLabel.setText("请填写所有字段！");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            try {
                int parsedSupplierID = Integer.parseInt(supplierID);
                int parsedBookID = Integer.parseInt(bookID);
                int parsedQuantity = Integer.parseInt(quantity);

                boolean success = PurchaseOrderService.addPurchaseOrder(parsedSupplierID, parsedBookID, parsedQuantity);
                if (success) {
                    resultLabel.setText("采购订单添加成功！");
                    resultLabel.setStyle("-fx-text-fill: green;");
                } else {
                    resultLabel.setText("采购订单添加失败！");
                    resultLabel.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("供应商ID、书籍ID和采购数量必须是整数！");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // 显示对话框
        Scene scene = new Scene(mainBox, 400, 300);
        addOrderStage.setScene(scene);
        addOrderStage.show();
    }
}