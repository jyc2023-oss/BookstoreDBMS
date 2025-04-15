package Util;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogUtil {

    /**
     * 显示一个确认对话框
     *
     * @param title     弹窗标题
     * @param message   提示信息
     * @param onConfirm 确认按钮的回调
     * @param onCancel  取消按钮的回调（可选，可传 null）
     */
    public static void showConfirmationDialog(String title, String message, Runnable onConfirm, Runnable onCancel) {
        // 创建一个新的 Stage（弹窗）
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.APPLICATION_MODAL); // 设置为模态窗口

        // 创建布局
        VBox vbox = new VBox(20);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new javafx.geometry.Insets(20));

        // 提示信息
        Label confirmationLabel = new Label(message);
        confirmationLabel.setStyle("-fx-font-size: 16px;");

        // 确定和取消按钮
        Button confirmButton = new Button("确定");
        confirmButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");
        Button cancelButton = new Button("取消");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 20;");

        // 按钮的布局
        HBox buttonBox = new HBox(20, confirmButton, cancelButton);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        // 确定按钮事件
        confirmButton.setOnAction(e -> {
            if (onConfirm != null) {
                onConfirm.run(); // 执行确认回调
            }
            dialogStage.close(); // 关闭弹窗
        });

        // 取消按钮事件
        cancelButton.setOnAction(e -> {
            if (onCancel != null) {
                onCancel.run(); // 执行取消回调（如果提供了）
            }
            dialogStage.close(); // 关闭弹窗
        });

        // 将控件添加到布局中
        vbox.getChildren().addAll(confirmationLabel, buttonBox);

        // 创建并显示弹窗
        Scene scene = new Scene(vbox, 300, 150);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
