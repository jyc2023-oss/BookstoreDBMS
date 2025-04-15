package Service;

import java.util.List;
import DAO.*;
import Model.Customer;

public class CustomerService {

    /**
     * 根据筛选条件查询顾客
     *
     * @param name     姓名筛选条件
     * @param gender   性别筛选条件
     * @param address  地址筛选条件
     * @param username 用户名筛选条件
     * @return 查询结果列表
     */
    public static List<Customer> queryCustomers(String name, String gender, String address,
            String username) {
        return CustomerDAO.queryCustomers(name, gender, address, username);
    }

    // 添加顾客
    public static boolean addCustomer(String name, String gender, String email, String phone,
            String address, String username, String password) {
        return CustomerDAO.addCustomer(name, gender, email, phone, address, username, password);
    }

    // 更新顾客信息
    public static String updateCustomer(int customerId, String name, String gender,
            String phone, String email, String address, String username) {
        return CustomerDAO.updateCustomer(customerId, name, gender, phone, email, address, username)
                ? "顾客信息更新成功！"
                : "顾客信息更新失败，请检查输入！";
    }

    // 删除顾客信息
    public static String deleteCustomer(int customerId) {
        boolean success = CustomerDAO.deleteCustomerById(customerId);

        if (success) {
            return "顾客删除成功！";
        } else {
            return "顾客删除失败，请检查是否存在该顾客 ID！";
        }
    }

    // 仅供顾客使用：获取自己的信息
    public static Customer getSelfInfo(String username) {
        return CustomerDAO.getCustomerByUsername(username);
    }

    // 仅供顾客使用：修改自己的信息
    public static boolean updateSelfInfo(int customerID, String name, String gender, String phone, String email,
            String address) {
        return CustomerDAO.updateCustomerInfo(customerID, name, gender, phone, email, address);
    }

}
