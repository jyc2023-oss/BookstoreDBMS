package Service;

import java.util.List;
import Model.PurchaseOrderView;
import DAO.PurchaseOrderDAO;

public class PurchaseOrderService {

    public static List<PurchaseOrderView> queryPurchaseOrders(
            String purchaseOrderID, String supplierID, String bookID,
            String supplierName, String bookTitle) {
        return PurchaseOrderDAO.queryPurchaseOrders(
                purchaseOrderID, supplierID, bookID, supplierName, bookTitle);
    }

    public static boolean addPurchaseOrder(int supplierID, int bookID, int quantity) {
        return PurchaseOrderDAO.addPurchaseOrder(supplierID, bookID, quantity);
    }
}