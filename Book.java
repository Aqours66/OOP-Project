public class Book {
    private int id;
    private String title;
    private String author;
    private String year;
    private String isbn;

    public Book(int id, String title, String author, String year, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getInfo() {
        return "ID: " + id + "\nTitle: " + title + "\nAuthor: " + author + "\nYear: " + year + "\nISBN: " + isbn;
    }

    public void setId(int id) {
        this.id = id;
    }
}
