package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Book {
    private SimpleIntegerProperty bookID;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty isbn;
    private SimpleDoubleProperty price;
    private SimpleStringProperty category;
    private SimpleIntegerProperty stockQuantity;
    private SimpleIntegerProperty reorderLevel;

    // 构造函数：不包含BookID，用于“添加书籍”页面
    public Book(String title, String author, String isbn, double price, String category, int stockQuantity,
            int reorderLevel) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleStringProperty(category);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.reorderLevel = new SimpleIntegerProperty(reorderLevel);
    }

    // 构造函数：包含BookID，用于“查看书籍”页面
    public Book(int bookID, String title, String author, String isbn, double price, String category, int stockQuantity,
            int reorderLevel) {
        this.bookID = new SimpleIntegerProperty(bookID); // 新增 BookID 属性
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleStringProperty(category);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.reorderLevel = new SimpleIntegerProperty(reorderLevel);
    }

    // 属性方法（Property）
    public SimpleIntegerProperty bookIDProperty() {
        return bookID;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public SimpleIntegerProperty stockQuantityProperty() {
        return stockQuantity;
    }

    public SimpleIntegerProperty reorderLevelProperty() {
        return reorderLevel;
    }

    // Getters
    public String getTitle() {
        return title.get();
    }

    public int getStockQuantity() {
        return stockQuantity.get();
    }

    public int getReorderLevel() {
        return reorderLevel.get();
    }
}
