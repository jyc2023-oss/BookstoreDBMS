package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.*;
import Model.Book;

public class BookDAO {
    /**
     * 添加书籍到数据库
     *
     * @param title         书名
     * @param author        作者
     * @param isbn          ISBN
     * @param price         价格
     * @param category      分类
     * @param stockQuantity 库存
     * @param reorderLevel  预警水平
     * @return 是否插入成功
     */
    public static boolean addBook(String title, String author, String isbn, double price, String category,
            int stockQuantity, int reorderLevel) {
        String query = "INSERT INTO Book (Title, Author, ISBN, Price, Category, StockQuantity, ReorderLevel) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, isbn);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, category);
            preparedStatement.setInt(6, stockQuantity);
            preparedStatement.setInt(7, reorderLevel);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            DatabaseUtil.handleException(e);
            return false;
        }
    }

    /**
     * 根据书籍 ID 删除书籍
     *
     * @param bookId 书籍的唯一标识符
     * @return 删除是否成功
     */
    public static boolean deleteBookById(int bookId) {
        String query = "DELETE FROM Book WHERE BookID = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // 如果有记录被删除，返回 true

        } catch (SQLException e) {
            DatabaseUtil.handleException(e); // 记录异常
            return false;
        }
    }

    /**
     * 根据筛选条件查询书籍
     *
     * @param title    书名筛选条件
     * @param author   作者筛选条件
     * @param category 分类筛选条件
     * @param isbn     ISBN 筛选条件
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 查询结果列表
     */
    public static List<Book> queryBooks(String title, String author, String category, String isbn, String minPrice,
            String maxPrice) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE " +
                "(? IS NULL OR Title LIKE ?) AND " +
                "(? IS NULL OR Author LIKE ?) AND " +
                "(? IS NULL OR Category LIKE ?) AND " +
                "(? IS NULL OR ISBN LIKE ?) AND " +
                "(Price >= ?) AND (Price <= ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 设置筛选条件的参数
            preparedStatement.setString(1, title.isEmpty() ? null : title);
            preparedStatement.setString(2, title.isEmpty() ? null : "%" + title + "%");
            preparedStatement.setString(3, author.isEmpty() ? null : author);
            preparedStatement.setString(4, author.isEmpty() ? null : "%" + author + "%");
            preparedStatement.setString(5, category.isEmpty() ? null : category);
            preparedStatement.setString(6, category.isEmpty() ? null : "%" + category + "%");
            preparedStatement.setString(7, isbn.isEmpty() ? null : isbn);
            preparedStatement.setString(8, isbn.isEmpty() ? null : "%" + isbn + "%");

            // 如果价格筛选条件为空，设置为默认值
            double minPriceValue = minPrice.isEmpty() ? 0 : Double.parseDouble(minPrice);
            double maxPriceValue = maxPrice.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPrice);
            preparedStatement.setDouble(9, minPriceValue);
            preparedStatement.setDouble(10, maxPriceValue);

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("BookID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("ISBN"),
                        resultSet.getDouble("Price"),
                        resultSet.getString("Category"),
                        resultSet.getInt("StockQuantity"),
                        resultSet.getInt("ReorderLevel"));
                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     * 更新书籍信息（仅更新非空字段）
     *
     * @param bookID        书籍ID
     * @param title         书名（可选）
     * @param author        作者（可选）
     * @param isbn          ISBN（可选）
     * @param price         价格（可选）
     * @param category      分类（可选）
     * @param stockQuantity 库存数量（可选）
     * @param reorderLevel  预警水平（可选）
     * @return 更新是否成功
     */
    public static boolean updateBook(int bookID, String title, String author, String isbn, String price,
            String category, String stockQuantity, String reorderLevel) {
        // SQL 查询语句使用 COALESCE 处理空值
        String query = "UPDATE Book SET " +
                "Title = COALESCE(?, Title), " +
                "Author = COALESCE(?, Author), " +
                "ISBN = COALESCE(?, ISBN), " +
                "Price = COALESCE(?, Price), " +
                "Category = COALESCE(?, Category), " +
                "StockQuantity = COALESCE(?, StockQuantity), " +
                "ReorderLevel = COALESCE(?, ReorderLevel) " +
                "WHERE BookID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 设置参数值（如果字段为空，传入 NULL）
            preparedStatement.setString(1, title.isEmpty() ? null : title);
            preparedStatement.setString(2, author.isEmpty() ? null : author);
            preparedStatement.setString(3, isbn.isEmpty() ? null : isbn);
            preparedStatement.setObject(4, price.isEmpty() ? null : Double.parseDouble(price));
            preparedStatement.setString(5, category.isEmpty() ? null : category);
            preparedStatement.setObject(6, stockQuantity.isEmpty() ? null : Integer.parseInt(stockQuantity));
            preparedStatement.setObject(7, reorderLevel.isEmpty() ? null : Integer.parseInt(reorderLevel));
            preparedStatement.setInt(8, bookID); // 最后一个参数是 BookID

            // 执行更新操作
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
