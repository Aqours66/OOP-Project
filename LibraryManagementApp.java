import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibraryManagementApp extends JFrame {
    private Library library;

    private boolean loggedIn = false;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton displayButton;

    private JButton addPatronButton;
    private JButton updatePatronButton;
    private JButton deletePatronButton;
    private JButton displayPatronsButton;

    private JButton addTransactionButton;
    private JButton checkoutBookButton;
    private JButton returnBookButton;
    private JButton displayTransactionsButton;

    private void customizeButton(JButton button, Color baseColor) {
        // Apply button styles
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    private boolean isValidISBN(String isbn) {
        isbn = isbn.replaceAll("-", "").replaceAll(" ", ""); // Remove dashes and spaces
        return isbn.length() == 13;
    }

    private boolean isValidYear(String year) {
        return year.matches("\\d{4}"); // Check if year consists of exactly 4 digits
    }

    // Customize button appearance
    int buttonWidth = 250; // Set the desired width for buttons
    int buttonHeight = 80; // Set the desired height for buttons
    Font buttonFont = new Font("Arial", Font.BOLD, 18); // Customize font and size

    private void setButtonsEnabled(boolean enabled) {
        addButton.setEnabled(enabled && loggedIn);
        updateButton.setEnabled(enabled && loggedIn);
        deleteButton.setEnabled(enabled && loggedIn);
        displayButton.setEnabled(enabled && loggedIn);

        addPatronButton.setEnabled(enabled && loggedIn);
        updatePatronButton.setEnabled(enabled && loggedIn);
        deletePatronButton.setEnabled(enabled && loggedIn);
        displayPatronsButton.setEnabled(enabled && loggedIn);

        addTransactionButton.setEnabled(enabled && loggedIn);
        checkoutBookButton.setEnabled(enabled && loggedIn);
        returnBookButton.setEnabled(enabled && loggedIn);
        displayTransactionsButton.setEnabled(enabled && loggedIn);
    }

    private JTable createTable(String[] columnHeaders, Object[][] data) {
        JTable table = new JTable(data, columnHeaders);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 16)); // Customize header font
        table.setFont(new Font("Calibri", Font.PLAIN, 14)); // Customize cell font
        return table;
    }

    public LibraryManagementApp() {
        library = new Library();

        addButton = new JButton("Add Book");
        updateButton = new JButton("Update Book");
        deleteButton = new JButton("Delete Book");
        displayButton = new JButton("Display Books");

        addPatronButton = new JButton("Add Patron");
        updatePatronButton = new JButton("Update Patron");
        deletePatronButton = new JButton("Delete Patron");
        displayPatronsButton = new JButton("Display Patrons");

        addTransactionButton = new JButton("Add Transaction");
        checkoutBookButton = new JButton("Checkout Book");
        returnBookButton = new JButton("Return Book");
        displayTransactionsButton = new JButton("Display Transactions");

        // Layout setup
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);

        buttonPanel.add(addPatronButton);
        buttonPanel.add(updatePatronButton);
        buttonPanel.add(deletePatronButton);
        buttonPanel.add(displayPatronsButton);

        buttonPanel.add(addTransactionButton);
        buttonPanel.add(checkoutBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(displayTransactionsButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        Color primaryColor = new Color(52, 152, 219);
        Color secondaryColor = new Color(241, 196, 15);
        Color successColor = new Color(46, 204, 113);
        Color dangerColor = new Color(231, 76, 60);
        Color darkColor = new Color(44, 62, 80);

        customizeButton(addButton, primaryColor);
        customizeButton(updateButton, successColor);
        customizeButton(deleteButton, dangerColor);
        customizeButton(displayButton, secondaryColor);
        customizeButton(addPatronButton, primaryColor);
        customizeButton(updatePatronButton, successColor);
        customizeButton(deletePatronButton, dangerColor);
        customizeButton(displayPatronsButton, secondaryColor);
        customizeButton(addTransactionButton, primaryColor);
        customizeButton(checkoutBookButton, successColor);
        customizeButton(returnBookButton, dangerColor);
        customizeButton(displayTransactionsButton, secondaryColor);

        // Update the preferred dimensions for the buttons
        addButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        updateButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        deleteButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        displayButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addPatronButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        updatePatronButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        deletePatronButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        displayPatronsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addTransactionButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        checkoutBookButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        returnBookButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        displayTransactionsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        addButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        displayButton.setFont(buttonFont);

        addPatronButton.setFont(buttonFont);
        updatePatronButton.setFont(buttonFont);
        deletePatronButton.setFont(buttonFont);
        displayPatronsButton.setFont(buttonFont);

        addTransactionButton.setFont(buttonFont);
        checkoutBookButton.setFont(buttonFont);
        returnBookButton.setFont(buttonFont);
        displayTransactionsButton.setFont(buttonFont);

        addButton.setToolTipText("Add a new book");
        updateButton.setToolTipText("Update book information");
        deleteButton.setToolTipText("Delete selected book");
        displayButton.setToolTipText("Display Book");

        addPatronButton.setToolTipText("Add a new patron");
        updatePatronButton.setToolTipText("Update Patron information");
        deletePatronButton.setToolTipText("Delete selected Patron");
        displayPatronsButton.setToolTipText("Display Patron");

        addTransactionButton.setToolTipText("Add a new transaction");
        checkoutBookButton.setToolTipText("Checkout Selected Book");
        returnBookButton.setToolTipText("Delete selected transaction");
        displayTransactionsButton.setToolTipText("Displaty transaction");

        addButton.setIcon(IconUtils.resizeIcon("Image/addBook.png", 30, 30));
        updateButton.setIcon(IconUtils.resizeIcon("Image/updateBook.png", 30, 30));
        deleteButton.setIcon(IconUtils.resizeIcon("Image/deleteBook.png", 30, 30));
        displayButton.setIcon(IconUtils.resizeIcon("Image/displayBook.png", 30, 30));

        addPatronButton.setIcon(IconUtils.resizeIcon("Image/add.png", 30, 30));
        updatePatronButton.setIcon(IconUtils.resizeIcon("Image/update.png", 30, 30));
        deletePatronButton.setIcon(IconUtils.resizeIcon("Image/delete.png", 30, 30));
        displayPatronsButton.setIcon(IconUtils.resizeIcon("Image/display.png", 30, 30));

        addTransactionButton.setIcon(IconUtils.resizeIcon("Image/add.png", 30, 30));
        checkoutBookButton.setIcon(IconUtils.resizeIcon("Image/update.png", 30, 30));
        returnBookButton.setIcon(IconUtils.resizeIcon("Image/delete.png", 30, 30));
        displayTransactionsButton.setIcon(IconUtils.resizeIcon("Image/display.png", 30, 30));

        JButton registerButton = new JButton("Register User");
        customizeButton(registerButton, primaryColor);
        buttonPanel.add(registerButton);
        registerButton.setFont(buttonFont);
        registerButton.setToolTipText("Register");
        registerButton.setIcon(IconUtils.resizeIcon("Image/register.png", 30, 30));

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter username:");
                String password = JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter password:");

                if (username != null && password != null) {
                    library.registerUser(username, password);
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "User registered successfully!");
                }
            }
        });

        JButton loginButton = new JButton("Login");
        customizeButton(loginButton, successColor);
        buttonPanel.add(loginButton);
        loginButton.setFont(buttonFont);
        loginButton.setToolTipText("Login");
        loginButton.setIcon(IconUtils.resizeIcon("Image/login.png", 30, 30));

        // ActionListener for the loginButton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter username:");
                String password = JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter password:");

                if (username != null && password != null) {
                    if (library.authenticateUser(username, password)) {
                        loggedIn = true; // Mark user as logged in
                        setButtonsEnabled(true); // Enable buttons after successful login
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Login successful!");
                    } else {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Login failed. Invalid credentials.");
                    }
                }
            }
        });

        JButton logoutButton = new JButton("Logout");
        customizeButton(logoutButton, darkColor);
        buttonPanel.add(logoutButton);
        logoutButton.setFont(buttonFont);
        logoutButton.setToolTipText("Logout");
        logoutButton.setIcon(IconUtils.resizeIcon("Image/logout.png", 30, 30));

        // ActionListener for the logoutButton
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!loggedIn) {
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "You must log in first.",
                            "Logout Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                loggedIn = false; // Update login status
                setButtonsEnabled(false); // Disable buttons on logout

                JOptionPane.showMessageDialog(LibraryManagementApp.this, "Logout successful!");
            }
        });

        setButtonsEnabled(false);
        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Title:"));
                    JTextField titleField = new JTextField();
                    panel.add(titleField);
                    panel.add(new JLabel("Author:"));
                    JTextField authorField = new JTextField();
                    panel.add(authorField);
                    panel.add(new JLabel("Year:"));
                    JTextField yearField = new JTextField();
                    panel.add(yearField);
                    panel.add(new JLabel("ISBN:"));
                    JTextField isbnField = new JTextField();
                    panel.add(isbnField);

                    int result = JOptionPane.showConfirmDialog(
                            null,
                            panel,
                            "Add Book",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String title = titleField.getText();
                        String author = authorField.getText();
                        String year = yearField.getText();
                        String isbn = isbnField.getText();

                        if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty() && !isbn.isEmpty()) {
                            if (isValidISBN(isbn)) {
                                if (isValidYear(year)) {
                                    Book book = new Book(-1, title, author, year, isbn);
                                    library.addBook(book);

                                    JOptionPane.showMessageDialog(
                                            LibraryManagementApp.this,
                                            "Book added successfully!",
                                            "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(
                                            LibraryManagementApp.this,
                                            "Invalid year format. Year should be a 4-digit number.",
                                            "Invalid Year",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        LibraryManagementApp.this,
                                        "Invalid ISBN format. ISBN should be 13 digits without spaces or dashes.",
                                        "Invalid ISBN",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    LibraryManagementApp.this,
                                    "Please fill in all fields.",
                                    "Incomplete Fields",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Book ID:"));
                    JTextField bookIdField = new JTextField();
                    panel.add(bookIdField);
                    panel.add(new JLabel("Title:"));
                    JTextField titleField = new JTextField();
                    panel.add(titleField);
                    panel.add(new JLabel("Author:"));
                    JTextField authorField = new JTextField();
                    panel.add(authorField);
                    panel.add(new JLabel("Year:"));
                    JTextField yearField = new JTextField();
                    panel.add(yearField);
                    panel.add(new JLabel("ISBN:"));
                    JTextField isbnField = new JTextField();
                    panel.add(isbnField);

                    int result = JOptionPane.showConfirmDialog(
                            null,
                            panel,
                            "Update Book",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String title = titleField.getText();
                        String author = authorField.getText();
                        String year = yearField.getText();
                        String isbn = isbnField.getText();

                        if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty() && !isbn.isEmpty()) {
                            try {
                                int selectedBookId = Integer.parseInt(bookIdField.getText());
                                Book updatedBook = new Book(selectedBookId, title, author, year, isbn);

                                library.updateBook(updatedBook);
                                JOptionPane.showMessageDialog(
                                        LibraryManagementApp.this,
                                        "Book updated successfully!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(
                                        LibraryManagementApp.this,
                                        "Invalid book ID format.",
                                        "Invalid Input",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    LibraryManagementApp.this,
                                    "Please fill in all fields.",
                                    "Incomplete Fields",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int selectedBookId = Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    LibraryManagementApp.this,
                                    "Enter Book ID to Delete:"));

                    int confirmDelete = JOptionPane.showConfirmDialog(
                            LibraryManagementApp.this,
                            "Are you sure you want to delete this book?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmDelete == JOptionPane.YES_OPTION) {
                        try {
                            String deleteSql = "DELETE FROM books WHERE id = ?";
                            PreparedStatement deleteStatement = library.getConnection().prepareStatement(deleteSql);
                            deleteStatement.setInt(1, selectedBookId);
                            int rowsAffected = deleteStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(
                                        LibraryManagementApp.this,
                                        "Book deleted successfully!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(
                                        LibraryManagementApp.this,
                                        "Book with ID " + selectedBookId + " not found in the database.",
                                        "Not Found",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(
                                    LibraryManagementApp.this,
                                    "Error deleting book: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "Invalid book ID format.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.List<Book> books = library.getAllBooks();
                String[] columnHeaders = { "ID", "Title", "Author", "Year", "ISBN" };
                Object[][] data = new Object[books.size()][5];

                for (int i = 0; i < books.size(); i++) {
                    Book book = books.get(i);
                    data[i] = new Object[] { book.getId(), book.getTitle(), book.getAuthor(), book.getYear(),
                            book.getIsbn() };
                }

                JTable table = createTable(columnHeaders, data);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 400));
                JOptionPane.showMessageDialog(
                        LibraryManagementApp.this,
                        scrollPane,
                        "Book List",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        addPatronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                JTextField nameField = new JTextField();
                panel.add(nameField);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Add Patron",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();

                    if (!name.isEmpty()) {
                        Patron patron = new Patron(-1, name);
                        library.addPatron(patron);
                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Patron added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Please enter a name.",
                                "Incomplete Information",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        updatePatronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Patron ID:"));
                JTextField patronIdField = new JTextField();
                panel.add(patronIdField);
                panel.add(new JLabel("New Name:"));
                JTextField newNameField = new JTextField();
                panel.add(newNameField);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Update Patron",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String newName = newNameField.getText();

                    if (!newName.isEmpty()) {
                        try {
                            int selectedPatronId = Integer.parseInt(patronIdField.getText());
                            Patron updatedPatron = new Patron(selectedPatronId, newName);

                            library.updatePatron(updatedPatron);
                            JOptionPane.showMessageDialog(
                                    LibraryManagementApp.this,
                                    "Patron updated successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(
                                    LibraryManagementApp.this,
                                    "Invalid patron ID format.",
                                    "Invalid Input",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Please enter a new name.",
                                "Incomplete Information",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        deletePatronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int selectedPatronId = Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    LibraryManagementApp.this,
                                    "Enter Patron ID to Delete:"));

                    if (!library.isPatronExists(selectedPatronId)) {
                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Patron with ID " + selectedPatronId + " not found in the database.",
                                "Patron Not Found",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int confirmDelete = JOptionPane.showConfirmDialog(
                            LibraryManagementApp.this,
                            "Are you sure you want to delete this patron?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmDelete == JOptionPane.YES_OPTION) {
                        library.deletePatron(selectedPatronId);

                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Patron deleted successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "Invalid patron ID format.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayPatronsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.List<Patron> patrons = library.getAllPatrons();
                String[] columnHeaders = { "ID", "Name" };
                Object[][] data = new Object[patrons.size()][2];

                for (int i = 0; i < patrons.size(); i++) {
                    Patron patron = patrons.get(i);
                    data[i] = new Object[] { patron.getId(), patron.getName() };
                }

                JTable table = createTable(columnHeaders, data);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 400));
                JOptionPane.showMessageDialog(
                        LibraryManagementApp.this,
                        scrollPane,
                        "Patron List",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Book ID:"));
                JTextField bookIdField = new JTextField();
                panel.add(bookIdField);
                panel.add(new JLabel("Patron ID:"));
                JTextField patronIdField = new JTextField();
                panel.add(patronIdField);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Add Transaction",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int bookId = Integer.parseInt(bookIdField.getText());
                        int patronId = Integer.parseInt(patronIdField.getText());

                        library.checkoutBook(bookId, patronId);
                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Transaction added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Invalid input format.",
                                "Invalid Input",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        checkoutBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int bookId = Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    LibraryManagementApp.this,
                                    "Enter Book ID to Checkout:"));
                    int patronId = Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    LibraryManagementApp.this,
                                    "Enter Patron ID:"));

                    library.checkoutBook(bookId, patronId);

                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "Book checked out successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "Invalid input format.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int transactionId = Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    LibraryManagementApp.this,
                                    "Enter Transaction ID to Return:"));

                    int confirmReturn = JOptionPane.showConfirmDialog(
                            LibraryManagementApp.this,
                            "Are you sure you want to return this book?",
                            "Confirm Return",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmReturn == JOptionPane.YES_OPTION) {
                        // Return the book based on the transaction ID
                        library.returnBook(transactionId);

                        JOptionPane.showMessageDialog(
                                LibraryManagementApp.this,
                                "Book returned successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "Invalid input format.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LibraryManagementApp.this,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.List<Transaction> transactions = library.getAllTransactions();
                String[] columnHeaders = { "ID", "Book ID", "Patron ID", "Checkout Date", "Due Date" };
                Object[][] data = new Object[transactions.size()][5];

                for (int i = 0; i < transactions.size(); i++) {
                    Transaction transaction = transactions.get(i);
                    data[i] = new Object[] { transaction.getId(), transaction.getBookId(), transaction.getPatronId(),
                            transaction.getCheckoutDate(), transaction.getDueDate() };
                }

                JTable table = createTable(columnHeaders, data);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 400)); // Adjust the dimensions as needed
                JOptionPane.showMessageDialog(LibraryManagementApp.this, scrollPane, "Transaction List",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // JFrame setup
        setTitle("Library Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack(); // Pack components to fit the preferred size
        setLocationRelativeTo(null);
        setVisible(true); // Set the frame visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementApp().setVisible(true);
            }
        });
    }
}
