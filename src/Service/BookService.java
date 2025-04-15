package Service;

import java.util.List;
import DAO.BookDAO;
import Model.Book;

public class BookService {

    // 删除书籍逻辑：验证 ID 并调用 DAO 层方法
    public static String deleteBook(int bookId) {
        if (bookId <= 0) {
            return "书籍 ID 无效，请输入正确的 ID！";
        }

        boolean success = BookDAO.deleteBookById(bookId);
        return success ? "书籍删除成功！" : "书籍删除失败，可能书籍 ID 不存在！";
    }

    /**
     * 查询书籍
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
        // 调用 DAO 层的查询方法
        return BookDAO.queryBooks(title, author, category, isbn, minPrice, maxPrice);
    }

    /**
     * 更新书籍信息
     *
     * @param bookID        书籍ID
     * @param title         书名
     * @param author        作者
     * @param isbn          ISBN
     * @param price         价格
     * @param category      分类
     * @param stockQuantity 库存数量
     * @param reorderLevel  预警水平
     * @return 操作结果消息
     */
    public static String updateBook(int bookID, String title, String author, String isbn, String price, String category,
            String stockQuantity, String reorderLevel) {
        // 调用 DAO 方法更新书籍
        boolean success = BookDAO.updateBook(
                bookID,
                title == null ? "" : title.trim(),
                author == null ? "" : author.trim(),
                isbn == null ? "" : isbn.trim(),
                price == null ? "" : price.trim(),
                category == null ? "" : category.trim(),
                stockQuantity == null ? "" : stockQuantity.trim(),
                reorderLevel == null ? "" : reorderLevel.trim());

        return success ? "书籍更新成功！" : "书籍更新失败，请检查输入信息！";
    }

}
