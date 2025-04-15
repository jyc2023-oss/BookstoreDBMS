package Admin;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import Model.*;
import Service.*;

public class OrderManagementTab {

    @SuppressWarnings({ "unchecked", "deprecation" })
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
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("订单ID");
        orderIdField.setPrefWidth(80);

        TextField customerUsernameField = new TextField();
        customerUsernameField.setPromptText("顾客用户名");
        customerUsernameField.setPrefWidth(140);

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

        // 分析按钮
        Button analyzeButton = new Button("分析");
        analyzeButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");

        HBox.setHgrow(orderIdField, Priority.ALWAYS);
        HBox.setHgrow(customerUsernameField, Priority.ALWAYS);
        HBox.setHgrow(bookIdField, Priority.ALWAYS);
        HBox.setHgrow(bookTitleField, Priority.ALWAYS);
        HBox.setHgrow(queryButton, Priority.NEVER);
        HBox.setHgrow(analyzeButton, Priority.NEVER);

        filterBox.getChildren().addAll(
                new Label("订单ID:"), orderIdField,
                new Label("顾客用户名:"), customerUsernameField,
                new Label("书籍ID:"), bookIdField,
                new Label("书名:"), bookTitleField,
                queryButton,
                analyzeButton);

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
        VBox.setVgrow(tableView, Priority.ALWAYS); // 让表格在 VBox 中占据尽可能多的垂直空间
        tableView.setMaxWidth(Double.MAX_VALUE); // 允许表格最大宽度扩展
        VBox.setMargin(tableView, new Insets(0, 0, 12, 0)); // 仅为表格设置底部外边距

        // 查询按钮事件
        queryButton.setOnAction(e -> {
            String orderId = orderIdField.getText().trim();
            String customerUsername = customerUsernameField.getText().trim();
            String bookId = bookIdField.getText().trim();
            String bookTitle = bookTitleField.getText().trim();

            // 调用 Service 查询订单
            List<OrderView> orders = OrderService.queryOrders(orderId, customerUsername, bookId, bookTitle);

            // 显示查询结果
            tableView.getItems().clear();
            tableView.getItems().addAll(orders);
        });

        // 分析按钮事件
        analyzeButton.setOnAction(e -> {
            // 添加分析逻辑（例如显示图表或数据分析结果）
            showOrderAnalysisDialog();
        });

        // 将控件加入主布局
        mainBox.getChildren().addAll(filterBox, tableView);

        return mainBox;
    }

    private void showOrderAnalysisDialog() {
        Stage analysisStage = new Stage();
        analysisStage.setTitle("管理员 - 订单分析");

        // 主布局
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        // 总订单数和总销售额
        Label totalOrdersLabel = new Label("总订单数: ");
        Label totalSalesLabel = new Label("总销售额: ");

        HBox summaryBox = new HBox(20, totalOrdersLabel, totalSalesLabel);
        summaryBox.setAlignment(Pos.CENTER);

        // 畅销书柱状图
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("书籍");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("销量");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("畅销书前 10");

        // 顾客购买占比饼图
        PieChart pieChart = new PieChart();
        pieChart.setTitle("顾客购买占比");

        // 调用分析数据的方法
        analyzeOrders(totalOrdersLabel, totalSalesLabel, barChart, pieChart);

        // 将控件添加到布局
        mainBox.getChildren().addAll(summaryBox, barChart, pieChart);

        // 场景和窗口
        Scene scene = new Scene(mainBox, 800, 600);
        analysisStage.setScene(scene);
        analysisStage.show();
    }

    private void analyzeOrders(Label totalOrdersLabel, Label totalSalesLabel,
            BarChart<String, Number> barChart, PieChart pieChart) {
        try {
            // 获取分析数据
            OrderAnalysisResult analysisResult = OrderService.analyzeOrders();

            // 设置总订单数和总销售额
            totalOrdersLabel.setText("总订单数: " + analysisResult.getTotalOrders());
            totalSalesLabel.setText("总销售额: ￥" + analysisResult.getTotalSales());

            // 设置畅销书柱状图数据
            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("畅销书销量");
            for (BookSales bookSales : analysisResult.getTopSellingBooks()) {
                series.getData().add(new XYChart.Data<>(bookSales.getBookTitle(), bookSales.getQuantitySold()));
            }
            barChart.getData().add(series);

            // 设置顾客购买占比饼图数据
            pieChart.getData().clear();
            for (CustomerSales customerSales : analysisResult.getCustomerSalesList()) {
                pieChart.getData()
                        .add(new PieChart.Data(customerSales.getCustomerName(), customerSales.getTotalSpent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}