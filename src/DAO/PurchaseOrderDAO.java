package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Util.DatabaseUtil;

public class PurchaseOrderDAO {

    public static List<PurchaseOrderView> queryPurchaseOrders(
            String purchaseOrderID, String supplierID, String bookID,
            String supplierName, String bookTitle) {
        List<PurchaseOrderView> purchaseOrders = new ArrayList<>();

        String query = "SELECT po.PurchaseOrderID, po.SupplierID, s.Name AS SupplierName, " +
                "po.BookID, b.Title AS BookTitle, po.Quantity, po.OrderDate " +
                "FROM PurchaseOrder po " +
                "JOIN Supplier s ON po.SupplierID = s.SupplierID " +
                "JOIN Book b ON po.BookID = b.BookID " +
                "WHERE (? IS NULL OR po.PurchaseOrderID = ?) " +
                "AND (? IS NULL OR po.SupplierID = ?) " +
                "AND (? IS NULL OR po.BookID = ?) " +
                "AND (? IS NULL OR s.Name LIKE ?) " +
                "AND (? IS NULL OR b.Title LIKE ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, purchaseOrderID.isEmpty() ? null : purchaseOrderID);
            preparedStatement.setString(2, purchaseOrderID.isEmpty() ? null : purchaseOrderID);
            preparedStatement.setString(3, supplierID.isEmpty() ? null : supplierID);
            preparedStatement.setString(4, supplierID.isEmpty() ? null : supplierID);
            preparedStatement.setString(5, bookID.isEmpty() ? null : bookID);
            preparedStatement.setString(6, bookID.isEmpty() ? null : bookID);
            preparedStatement.setString(7, supplierName.isEmpty() ? null : supplierName);
            preparedStatement.setString(8, supplierName.isEmpty() ? null : "%" + supplierName + "%");
            preparedStatement.setString(9, bookTitle.isEmpty() ? null : bookTitle);
            preparedStatement.setString(10, bookTitle.isEmpty() ? null : "%" + bookTitle + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PurchaseOrderView purchaseOrder = new PurchaseOrderView(
                        resultSet.getInt("PurchaseOrderID"),
                        resultSet.getInt("SupplierID"),
                        resultSet.getString("SupplierName"),
                        resultSet.getInt("BookID"),
                        resultSet.getString("BookTitle"),
                        resultSet.getInt("Quantity"),
                        resultSet.getString("OrderDate"));
                purchaseOrders.add(purchaseOrder);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrders;
    }

    // 添加采购订单
    public static boolean addPurchaseOrder(int supplierID, int bookID, int quantity) {
        String query = "INSERT INTO PurchaseOrder (SupplierID, BookID, Quantity) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, supplierID);
            preparedStatement.setInt(2, bookID);
            preparedStatement.setInt(3, quantity);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}