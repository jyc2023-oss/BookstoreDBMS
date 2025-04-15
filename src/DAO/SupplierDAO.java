package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Util.*;

public class SupplierDAO {

    /**
     * 根据筛选条件查询供应商
     *
     * @param name     名称筛选条件
     * @param address  地址筛选条件
     * @param username 用户名筛选条件
     * @return 查询到的供应商列表
     */
    public static List<Supplier> querySuppliers(String name, String address, String username) {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT SupplierID, Name, Phone, Email, Address, Username FROM Supplier WHERE " +
                "(? IS NULL OR Name LIKE ?) AND " +
                "(? IS NULL OR Address LIKE ?) AND " +
                "(? IS NULL OR Username LIKE ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 设置参数
            preparedStatement.setString(1, name.isEmpty() ? null : name);
            preparedStatement.setString(2, name.isEmpty() ? null : "%" + name + "%");
            preparedStatement.setString(3, address.isEmpty() ? null : address);
            preparedStatement.setString(4, address.isEmpty() ? null : "%" + address + "%");
            preparedStatement.setString(5, username.isEmpty() ? null : username);
            preparedStatement.setString(6, username.isEmpty() ? null : "%" + username + "%");

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt("SupplierID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Address"),
                        resultSet.getString("Username"));
                suppliers.add(supplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    /**
     * 添加供应商
     *
     * @param name     供应商名称
     * @param phone    联系方式
     * @param email    邮箱
     * @param address  地址
     * @param username 用户名
     * @param password 密码
     * @return 是否添加成功
     */
    public static boolean addSupplier(String name, String phone, String email, String address, String username,
            String password) {
        String query = "INSERT INTO Supplier (Name, phone, Email, Address, Username, Password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, username);
            preparedStatement.setString(6, PBKDF2Util.hashPassword(password, username)); // 对密码加密

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新供应商信息
     *
     * @param supplierID 供应商 ID
     * @param name       新名称
     * @param phone      新联系方式
     * @param email      新邮箱
     * @param address    新地址
     * @param username   新用户名
     * @return 是否更新成功
     */
    public static boolean updateSupplier(int supplierID, String name, String phone, String email, String address,
            String username) {
        String query = "UPDATE Supplier SET " +
                "Name = COALESCE(?, Name), " +
                "phone = COALESCE(?, phone), " +
                "Email = COALESCE(?, Email), " +
                "Address = COALESCE(?, Address), " +
                "Username = COALESCE(?, Username) " +
                "WHERE SupplierID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name.isEmpty() ? null : name);
            preparedStatement.setString(2, phone.isEmpty() ? null : phone);
            preparedStatement.setString(3, email.isEmpty() ? null : email);
            preparedStatement.setString(4, address.isEmpty() ? null : address);
            preparedStatement.setString(5, username.isEmpty() ? null : username);
            preparedStatement.setInt(6, supplierID);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除供应商
     *
     * @param supplierID 供应商 ID
     * @return 是否删除成功
     */
    public static boolean deleteSupplier(int supplierID) {
        String query = "DELETE FROM Supplier WHERE SupplierID = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, supplierID);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
