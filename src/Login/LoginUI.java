package Login;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import Admin.*;
import Customer.CustomerDashboard;
import Main.MainUI;

public class LoginUI extends Application {

    private String userType;

    public LoginUI(String userType) {
        this.userType = userType;
    }

    @Override
    public void start(Stage stage) {
        // 创建标题
        Label titleLabel = new Label(userType + "登录界面");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        titleLabel.setPadding(new Insets(0, 0, 20, 0)); // 调整标题位置

        // 创建控件
        Label usernameLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(200);

        Label passwordLabel = new Label("密码:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);

        // 登录按钮
        Button loginButton = new Button("登录");
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");

        // 登录结果提示
        Label loginResultLabel = new Label();
        loginResultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        loginResultLabel.setPadding(new Insets(10, 0, 0, 0)); // 文本位置

        // 布局设置
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15); // 减少控件之间的垂直间距
        gridPane.setPadding(new Insets(20, 20, 10, 20));

        // 添加控件到网格布局
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // 主布局
        VBox mainBox = new VBox(10, titleLabel, gridPane, loginButton, loginResultLabel);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(30));

        // 登录按钮事件
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (LoginValidator.validateLogin(userType, username, password)) {
                loginResultLabel.setText("登录成功！");
                loginResultLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
                // 使用 PauseTransition 延迟切换界面
                PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                pause.setOnFinished(event -> {
                    stage.close(); // 关闭当前登录窗口
                    // MainUI.getMainStage().close();
                    MainUI.closeMainUI();
                    openUserDashboard(userType, username);
                });
                pause.play(); // 开始计时
            } else {
                loginResultLabel.setText("登录失败，用户名或密码错误！");
                loginResultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });

        // 设置默认按钮，按下回车键触发登录
        loginButton.setDefaultButton(true);

        // 场景和窗口
        Scene scene = new Scene(mainBox, 400, 300); // 界面居中
        stage.setTitle(userType + "登录");
        stage.setScene(scene);
        stage.show();
    }

    // 打开对应的用户界面
    private void openUserDashboard(String userType, String username) {
        try {
            Stage dashboardStage = new Stage();
            if ("管理员".equals(userType)) {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.start(dashboardStage);
            } else if ("顾客".equals(userType)) {
                // 创建顾客的默认头像
                String avatarUrl = "https://bpic.588ku.com/original_origin_min_pic/19/10/04/a321f23dd346c61646a546ad9cc9194d.jpg!r650";
                // 实例化并启动顾客界面
                CustomerDashboard customerDashboard = new CustomerDashboard(username, avatarUrl);
                customerDashboard.start(dashboardStage);
            } else if ("供应商".equals(userType)) {
                // SupplierDashboard supplierDashboard = new SupplierDashboard();
                // supplierDashboard.start(dashboardStage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}