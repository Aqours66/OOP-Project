import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Library {
    private List<Book> books;
    private List<Patron> patrons;
    private Connection connection;

    public Library() {
        books = new ArrayList<>();
        patrons = new ArrayList<>();
        connectToDatabase();
    }

    public Connection getConnection() {
        return connection;
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/library"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        try {
            // Load the MySQL JDBC driver (this step is crucial)
            Class.forName("com.mysql.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

        public void registerUser(String username, String password) {
        User user = new User(username, password);

        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();

            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                User user = new User(username, storedPassword);
                return user.authenticate(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Patron getPatronByName(String name) {
        for (Patron patron : patrons) {
            if (patron.getName().equalsIgnoreCase(name)) {
                return patron;
            }
        }
        return null; // Patron not found
    }

    public Book getBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Book not found
    }

    public void addBook(Book book) {
        book.setId(books.size() + 1); // Assign a unique ID
        books.add(book);

        // Insert into database
        try {
            String sql = "INSERT INTO books (title, author, year, isbn) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getYear());
            statement.setString(4, book.getIsbn());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, year = ?, isbn = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getYear());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully");
            } else {
                System.out.println("Failed to update book");
            }
        }
    }

    public void deleteBook(int bookId) {
        // Delete the book from the database
        try {
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully");
            } else {
                System.out.println("Failed to delete book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Remove the book from the local list of books
        books.removeIf(book -> book.getId() == bookId);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String year = resultSet.getString("year");
                String isbn = resultSet.getString("isbn");
                books.add(new Book(id, title, author, year, isbn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public List<Patron> getAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        String query = "SELECT * FROM patrons";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                patrons.add(new Patron(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patrons;
    }

    public void updatePatron(Patron patron) {
        String sql = "UPDATE patrons SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patron.getName());
            statement.setInt(2, patron.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patron updated successfully");
            } else {
                System.out.println("Failed to update patron");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatron(int patronId) {
        String sql = "DELETE FROM patrons WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patronId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patron deleted successfully");
            } else {
                System.out.println("Failed to delete patron");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patron getPatronById(int id) {
        for (Patron patron : patrons) {
            if (patron.getId() == id) {
                return patron;
            }
        }
        return null; // Patron not found
    }

    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null; // Book not found
    }

    public void addPatron(Patron patron) {
        patron.setId(patrons.size() + 1); // Assign a unique ID
        patrons.add(patron);
        try {
            String sql = "INSERT INTO patrons (name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, patron.getName());
            statement.executeUpdate();

            // Retrieve the generated patron ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int patronId = generatedKeys.getInt(1);
                patron.setId(patronId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM transactions";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                int patronId = resultSet.getInt("patron_id");
                Date checkoutDate = resultSet.getDate("checkout_date");
                Date dueDate = resultSet.getDate("due_date");

                Transaction transaction = new Transaction(id, bookId, patronId, checkoutDate, dueDate);
                transactions.add(transaction);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void checkoutBook(int bookId, int patronId) {
        try {
            String query = "INSERT INTO transactions (book_id, patron_id, checkout_date, due_date) " +
                    "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY))";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            statement.setInt(2, patronId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int transactionId) {
        try {
            String query = "DELETE FROM transactions WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book.getInfo());
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
