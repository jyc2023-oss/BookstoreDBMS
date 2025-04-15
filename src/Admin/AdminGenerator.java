package Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Util.*;

public class AdminGenerator {

    public static void main(String[] args) {
        // 生成并插入管理员的用户名和密码
        // insertAdmin("管理员", "admin", "password");
    }

    public static void insertAdmin(String username, String plainPassword) {
        String tableName = "Admin";

        // 使用用户名作为盐值生成哈希密码
        String hashedPassword = PBKDF2Util.hashPassword(plainPassword, username);

        // 插入到数据库
        String query = "INSERT INTO " + tableName + " (Username, Password) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("管理员账号插入成功！ 用户名: " + username);
            } else {
                System.out.println("管理员账号插入失败！ 用户名: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}