package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Util.DatabaseUtil;

public class OrderDAO {

    // 根据筛选条件查询订单（管理员 / 顾客）
    public static List<OrderView> queryOrders(String orderId, String customerUsername, String bookId,
            String bookTitle) {
        List<OrderView> orders = new ArrayList<>();
        String query = "SELECT o.OrderID, c.Username AS CustomerUsername, o.BookID, b.Title AS BookTitle, " +
                "o.Quantity, o.TotalAmount, o.OrderDate " +
                "FROM Orders o " +
                "JOIN Customer c ON o.CustomerID = c.CustomerID " +
                "JOIN Book b ON o.BookID = b.BookID " +
                "WHERE (? IS NULL OR o.OrderID = ?) " +
                "AND (? IS NULL OR c.Username LIKE ?) " +
                "AND (? IS NULL OR o.BookID = ?) " +
                "AND (? IS NULL OR b.Title LIKE ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId.isEmpty() ? null : orderId);
            preparedStatement.setString(2, orderId.isEmpty() ? null : orderId);
            preparedStatement.setString(3, customerUsername.isEmpty() ? null : customerUsername);
            preparedStatement.setString(4, customerUsername.isEmpty() ? null : "%" + customerUsername + "%");
            preparedStatement.setString(5, bookId.isEmpty() ? null : bookId);
            preparedStatement.setString(6, bookId.isEmpty() ? null : bookId);
            preparedStatement.setString(7, bookTitle.isEmpty() ? null : bookTitle);
            preparedStatement.setString(8, bookTitle.isEmpty() ? null : "%" + bookTitle + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrderView order = new OrderView(
                        resultSet.getInt("OrderID"),
                        resultSet.getString("CustomerUsername"),
                        resultSet.getInt("BookID"),
                        resultSet.getString("BookTitle"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("TotalAmount"),
                        resultSet.getString("OrderDate"));
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 管理员：分析顾客订单
    public static OrderAnalysisResult analyzeOrders() {
        OrderAnalysisResult result = new OrderAnalysisResult();

        // 查询总订单数量和总销售额
        String totalOrdersQuery = "SELECT COUNT(*) AS TotalOrders, SUM(TotalAmount) AS TotalSales FROM Orders";

        // 查询最畅销的书籍（前10名）
        String topSellingBooksQuery = "SELECT b.Title, SUM(o.Quantity) AS QuantitySold " +
                "FROM Orders o " +
                "JOIN Book b ON o.BookID = b.BookID " +
                "GROUP BY b.Title " +
                "ORDER BY QuantitySold DESC " +
                "LIMIT 10";

        // 查询消费金额最高的顾客
        String customerSalesQuery = "SELECT c.Username, SUM(o.TotalAmount) AS TotalSpent " +
                "FROM Orders o " +
                "JOIN Customer c ON o.CustomerID = c.CustomerID " +
                "GROUP BY c.Username " +
                "ORDER BY TotalSpent DESC";

        try (Connection connection = DatabaseUtil.getConnection()) {
            // 查询总订单数量和总销售额
            try (PreparedStatement ps = connection.prepareStatement(totalOrdersQuery);
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result.setTotalOrders(rs.getInt("TotalOrders"));
                    result.setTotalSales(rs.getDouble("TotalSales"));
                }
            }

            // 查询最畅销的书籍
            try (PreparedStatement ps = connection.prepareStatement(topSellingBooksQuery);
                    ResultSet rs = ps.executeQuery()) {
                List<BookSales> topSellingBooks = new ArrayList<>();
                while (rs.next()) {
                    topSellingBooks.add(new BookSales(rs.getString("Title"), rs.getInt("QuantitySold")));
                }
                result.setTopSellingBooks(topSellingBooks);
            }

            // 查询消费金额最高的顾客
            try (PreparedStatement ps = connection.prepareStatement(customerSalesQuery);
                    ResultSet rs = ps.executeQuery()) {
                List<CustomerSales> customerSalesList = new ArrayList<>();
                while (rs.next()) {
                    customerSalesList.add(new CustomerSales(rs.getString("Username"), rs.getDouble("TotalSpent")));
                }
                result.setCustomerSalesList(customerSalesList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 检查库存是否充足
    public static boolean isStockAvailable(Connection connection, int bookId, int quantity) throws SQLException {
        String query = "SELECT StockQuantity FROM Book WHERE BookID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int stockQuantity = resultSet.getInt("StockQuantity");
                return stockQuantity >= quantity; // 库存是否足够
            }
        }
        return false; // 如果查询不到数据，返回库存不足
    }

    public static boolean insertOrder(Connection connection, int customerId, int bookId, int quantity)
            throws SQLException {
        String query = "INSERT INTO Orders (CustomerID, BookID, Quantity, OrderDate) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static boolean updateStock(Connection connection, int bookId, int quantity) throws SQLException {
        String query = "UPDATE Book SET StockQuantity = StockQuantity - ? WHERE BookID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, bookId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    // 获取顾客ID
    public static int getCustomerIdByUsername(Connection connection, String username) throws SQLException {
        String query = "SELECT CustomerID FROM Customer WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("CustomerID"); // 返回查询到的 CustomerID
            }
        }
        return -1; // 如果未找到对应的用户，返回 -1 表示无效
    }

}
