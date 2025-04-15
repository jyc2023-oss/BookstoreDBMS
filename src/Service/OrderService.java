package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import DAO.*;
import Util.DatabaseUtil;
import Model.*;

public class OrderService {

    // 根据筛选条件查询订单（管理员 / 顾客)
    public static List<OrderView> queryOrders(String orderId, String customerUsername, String bookId,
            String bookTitle) {
        return OrderDAO.queryOrders(orderId, customerUsername, bookId, bookTitle);
    }

    // 管理员：订单分析
    public static OrderAnalysisResult analyzeOrders() {
        return OrderDAO.analyzeOrders();
    }

    // 顾客添加订单
    public static boolean placeOrder(String username, int bookId, int quantity) {
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = DatabaseUtil.getConnection();
            connection.setAutoCommit(false); // 开启手动提交模式

            // 获取顾客ID
            int customerId = OrderDAO.getCustomerIdByUsername(connection, username);
            if (customerId == -1) {
                System.out.println("用户名不存在！");
                return false;
            }

            // 检查库存是否充足
            boolean stockAvailable = OrderDAO.isStockAvailable(connection, bookId, quantity);
            if (!stockAvailable) {
                System.out.println("库存不足！");
                return false;
            }

            // 插入订单
            boolean orderInserted = OrderDAO.insertOrder(connection, customerId, bookId, quantity);
            if (!orderInserted) {
                connection.rollback(); // 回滚事务
                return false;
            }

            // 更新库存
            boolean stockUpdated = OrderDAO.updateStock(connection, bookId, quantity);
            if (!stockUpdated) {
                connection.rollback(); // 回滚事务
                return false;
            }

            // 提交事务
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // 出现异常时回滚事务
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // 恢复自动提交模式
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

}
