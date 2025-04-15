package Model;

public class BookSales {
    private String bookTitle;
    private int quantitySold;

    public BookSales(String bookTitle, int quantitySold) {
        this.bookTitle = bookTitle;
        this.quantitySold = quantitySold;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getQuantitySold() {
        return quantitySold;
    }
}
