package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/BookStoreDB"; // 数据库地址
    private static final String DB_USER = "root"; // 数据库用户名
    private static final String DB_PASSWORD = "trilliverse"; // 数据库密码

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void handleException(Exception e) {
        e.printStackTrace();
        // 可以扩展记录日志等功能
    }
}
