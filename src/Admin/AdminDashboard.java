package Admin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    @Override
    public void start(Stage stage) {
        // 创建 TabPane 作为主布局
        TabPane tabPane = new TabPane();

        // 设置 TabPane 样式，调整 Tab 大小
        tabPane.setStyle("-fx-tab-max-height: 40px; -fx-tab-min-height: 40px; -fx-font-size: 16px;");

        // 添加书籍管理 Tab
        Tab bookManagementTab = new Tab("书籍管理");
        bookManagementTab.setContent(new BookManagementTab().createContent());
        bookManagementTab.setClosable(false);

        // 添加顾客管理 Tab
        Tab customerManagementTab = new Tab("顾客管理");
        customerManagementTab.setContent(new CustomerManagementTab().createContent());
        customerManagementTab.setClosable(false);

        // 添加订单管理 Tab
        Tab orderManagementTab = new Tab("订单管理");
        orderManagementTab.setContent(new OrderManagementTab().createContent());
        orderManagementTab.setClosable(false);

        // 添加供应商管理 Tab
        Tab supplierManagementTab = new Tab("供应商管理");
        supplierManagementTab.setContent(SupplierManagementTab.createContent());
        supplierManagementTab.setClosable(false);

        // 添加采购订单管理 Tab
        Tab purchaseOrderManagementTab = new Tab("采购订单管理");
        purchaseOrderManagementTab.setContent(new PurchaseOrderManagementTab().createContent());
        purchaseOrderManagementTab.setClosable(false);

        // 将 Tabs 添加到 TabPane
        tabPane.getTabs().addAll(
                bookManagementTab,
                customerManagementTab,
                orderManagementTab,
                supplierManagementTab,
                purchaseOrderManagementTab);

        // 设置默认选中的 Tab 为 bookManagementTab
        tabPane.getSelectionModel().select(bookManagementTab);

        // 场景和窗口
        Scene scene = new Scene(tabPane, 1000, 620);
        stage.setTitle("管理员后台 - 网上书店综合管理系统");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}