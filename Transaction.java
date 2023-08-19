import java.util.Date;

public class Transaction {
    private int id;
    private int bookId; // Add this field
    private int patronId; // Add this field
    private Date checkoutDate;
    private Date dueDate;

    public Transaction(int id, int bookId, int patronId, Date checkoutDate, Date dueDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getPatronId() {
        return patronId;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
