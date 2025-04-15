package Service;

import java.util.List;
import Model.Supplier;
import DAO.SupplierDAO;

public class SupplierService {

    /**
     * 查询供应商信息
     *
     * @param name     名称筛选条件
     * @param address  地址筛选条件
     * @param username 用户名筛选条件
     * @return 查询到的供应商列表
     */
    public static List<Supplier> querySuppliers(String name, String address, String username) {
        return SupplierDAO.querySuppliers(name, address, username);
    }

    public static boolean addSupplier(String name, String phone, String email, String address, String username,
            String password) {
        return SupplierDAO.addSupplier(name, phone, email, address, username, password);
    }

    public static boolean updateSupplier(int supplierID, String name, String phone, String email, String address,
            String username) {
        return SupplierDAO.updateSupplier(supplierID, name, phone, email, address, username);
    }

    public static boolean deleteSupplier(int supplierID) {
        return SupplierDAO.deleteSupplier(supplierID);
    }

}
