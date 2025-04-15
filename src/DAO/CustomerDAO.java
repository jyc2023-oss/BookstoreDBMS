package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Customer;
import Util.*;

public class CustomerDAO {

    /**
     * 根据筛选条件查询顾客
     *
     * @param name     顾客姓名筛选条件
     * @param gender   性别筛选条件
     * @param address  地址筛选条件
     * @param username 用户名筛选条件
     * @return 查询结果列表
     */
    public static List<Customer> queryCustomers(String name, String gender, String address, String username) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer WHERE " +
                "(? IS NULL OR Name LIKE ?) AND " +
                "(? IS NULL OR Gender LIKE ?) AND " +
                "(? IS NULL OR Address LIKE ?) AND " +
                "(? IS NULL OR Username LIKE ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 设置筛选条件
            preparedStatement.setString(1, name.isEmpty() ? null : name);
            preparedStatement.setString(2, name.isEmpty() ? null : "%" + name + "%");
            preparedStatement.setString(3, gender.isEmpty() ? null : gender);
            preparedStatement.setString(4, gender.isEmpty() ? null : "%" + gender + "%");
            preparedStatement.setString(5, address.isEmpty() ? null : address);
            preparedStatement.setString(6, address.isEmpty() ? null : "%" + address + "%");
            preparedStatement.setString(7, username.isEmpty() ? null : username);
            preparedStatement.setString(8, username.isEmpty() ? null : "%" + username + "%");

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // 创建 Customer 对象并添加到结果列表
                Customer customer = new Customer(
                        resultSet.getInt("CustomerID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Address"),
                        resultSet.getString("Username"));
                customers.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

    // 添加顾客
    public static boolean addCustomer(String name, String gender, String email, String phone,
            String address, String username, String password) {
        String query = "INSERT INTO Customer (Name, Gender, Email, Phone, Address, Username, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);

            // 假设使用 PBKDF2 哈希存储密码
            String hashedPassword = PBKDF2Util.hashPassword(password, username);
            preparedStatement.setString(7, hashedPassword);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新顾客信息
    public static boolean updateCustomer(int customerId, String name, String gender,
            String phone, String email, String address, String username) {
        String query = "UPDATE Customer SET " +
                "Name = COALESCE(?, Name), " +
                "Gender = COALESCE(?, Gender), " +
                "Phone = COALESCE(?, Phone), " +
                "Email = COALESCE(?, Email), " +
                "Address = COALESCE(?, Address), " +
                "Username = COALESCE(?, Username) " +
                "WHERE CustomerID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name.isEmpty() ? null : name);
            preparedStatement.setString(2, gender == null ? null : gender);
            preparedStatement.setString(3, phone.isEmpty() ? null : phone);
            preparedStatement.setString(4, email.isEmpty() ? null : email);
            preparedStatement.setString(5, address.isEmpty() ? null : address);
            preparedStatement.setString(6, username.isEmpty() ? null : username);
            preparedStatement.setInt(7, customerId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据顾客ID删除顾客信息
    public static boolean deleteCustomerById(int customerId) {
        String query = "DELETE FROM Customer WHERE CustomerID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定用户名的顾客信息（仅用于顾客查看自己的信息）
     *
     * @param username 用户名
     * @return 对应的 Customer 对象
     */
    public static Customer getCustomerByUsername(String username) {
        String query = "SELECT * FROM Customer WHERE Username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Customer(
                        resultSet.getInt("CustomerID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Address"),
                        resultSet.getString("Username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新顾客自己的信息（限制字段范围）
     *
     * @param customerId 顾客ID
     * @param email      新的邮箱
     * @param phone      新的联系电话
     * @return 更新是否成功
     */

    public static boolean updateCustomerInfo(int customerId, String name, String gender,
            String phone, String email, String address) {
        String query = "UPDATE Customer SET " +
                "Name = COALESCE(?, Name), " +
                "Gender = COALESCE(?, Gender), " +
                "Phone = COALESCE(?, Phone), " +
                "Email = COALESCE(?, Email), " +
                "Address = COALESCE(?, Address) " + // 顾客无法修改用户名
                "WHERE CustomerID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name.isEmpty() ? null : name);
            preparedStatement.setString(2, gender == null ? null : gender);
            preparedStatement.setString(3, phone.isEmpty() ? null : phone);
            preparedStatement.setString(4, email.isEmpty() ? null : email);
            preparedStatement.setString(5, address.isEmpty() ? null : address);
            preparedStatement.setInt(6, customerId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
