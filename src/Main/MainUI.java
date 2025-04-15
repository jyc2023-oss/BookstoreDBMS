package Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Login.LoginUI;

public class MainUI extends Application {

    private static Stage primaryStage; // 保存主窗口的引用

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // 存储主窗口引用

        // 顶部图片
        ImageView imageView = new ImageView(new Image("file:src/Image/MainUI_logo.png"));
        imageView.setFitWidth(970); // 设置图片宽度为整个窗口宽度
        imageView.setPreserveRatio(true); // 保持图片比例
        imageView.setSmooth(true); // 启用平滑处理

        // 图片包装在 HBox 中，确保居中
        HBox imageBox = new HBox(imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(10));

        // 创建标题
        Label titleLabel = new Label("网上书店综合管理系统");
        titleLabel.setStyle("-fx-font-size: 55px; -fx-font-weight: bold;");

        // 创建按钮
        Button adminButton = new Button("管理员入口");
        Button customerButton = new Button("顾客入口");
        // Button supplierButton = new Button("供应商入口");

        // 设置按钮样式
        adminButton.setStyle("-fx-font-size: 20px; -fx-padding: 20 40;");
        customerButton.setStyle("-fx-font-size: 20px; -fx-padding: 20 40;");
        // supplierButton.setStyle("-fx-font-size: 20px; -fx-padding: 15 30;");

        // 设置按钮点击事件
        adminButton.setOnAction(e -> openLoginUI("管理员"));
        customerButton.setOnAction(e -> openLoginUI("顾客"));
        // supplierButton.setOnAction(e -> openLoginUI("供应商"));

        Platform.runLater(() -> customerButton.requestFocus());

        // 布局按钮
        // HBox buttonBox = new HBox(40, adminButton, customerButton, supplierButton);
        HBox buttonBox = new HBox(40, adminButton, customerButton);
        buttonBox.setAlignment(Pos.CENTER); // 按钮居中
        buttonBox.setPadding(new Insets(30));

        // 中间布局
        VBox centerBox = new VBox(70, titleLabel, buttonBox);
        centerBox.setAlignment(Pos.CENTER);

        // 使用 BorderPane 布局
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(imageBox); // 将图片放置在顶部
        borderPane.setCenter(centerBox); // 将标题和按钮放置在中心

        // 场景和主窗口
        Scene scene = new Scene(borderPane, 1000, 620); // 设置窗口尺寸
        primaryStage.setTitle("网上书店综合管理系统 - 登录");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 打开登录界面
    private void openLoginUI(String userType) {
        try {
            LoginUI loginUI = new LoginUI(userType);
            Stage loginStage = new Stage();
            loginUI.start(loginStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 提供关闭主界面的方法
    public static void closeMainUI() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
