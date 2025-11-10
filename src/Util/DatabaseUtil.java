package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DB_DRIVER = "org.opengauss.Driver"; // openGauss JDBC 驱动
    private static final String DB_URL = "jdbc:opengauss://localhost:5432/BookStoreDB"; // 数据库地址
    private static final String DB_USER = "gaussdb"; // 数据库用户名
    private static final String DB_PASSWORD = "trilliverse"; // 数据库密码

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("无法加载 openGauss JDBC 驱动: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void handleException(Exception e) {
        e.printStackTrace();
        // 可以扩展记录日志等功能
    }
}
