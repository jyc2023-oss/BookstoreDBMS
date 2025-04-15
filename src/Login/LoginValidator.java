package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Util.*;

public class LoginValidator {

    public static boolean validateLogin(String userType, String username, String plainPassword) {
        String tableName = ""; // 根据角色设置对应的表名
        switch (userType) {
            case "管理员":
                tableName = "Admin";
                break;
            case "顾客":
                tableName = "Customer";
                break;
            case "供应商":
                tableName = "Supplier";
                break;
            default:
                return false; // 未知用户类型
        }

        String query = "SELECT Password FROM " + tableName + " WHERE Username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("Password");
                return PBKDF2Util.verifyPassword(plainPassword, username, storedHashedPassword);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // 用户名不存在或密码不匹配
    }
}