import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibraryManagementApp extends JFrame {
    private Library library;

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

    private void setButtonsEnabled(boolean enabled) {
        addButton.setEnabled(enabled);
        updateButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
        displayButton.setEnabled(enabled);

        addPatronButton.setEnabled(enabled);
        updatePatronButton.setEnabled(enabled);
        deletePatronButton.setEnabled(enabled);
        displayPatronsButton.setEnabled(enabled);

        addTransactionButton.setEnabled(enabled);
        checkoutBookButton.setEnabled(enabled);
        returnBookButton.setEnabled(enabled);
        displayTransactionsButton.setEnabled(enabled);
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
        JPanel buttonPanel = new JPanel(new GridLayout(3, 4, 10, 10));

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

        customizeButton(addButton, new Color(52, 152, 219));
        customizeButton(updateButton, new Color(46, 204, 113));
        customizeButton(deleteButton, new Color(231, 76, 60));
        customizeButton(displayButton, new Color(241, 196, 15));
        customizeButton(addPatronButton, new Color(155, 89, 182));
        customizeButton(updatePatronButton, new Color(192, 57, 43));
        customizeButton(deletePatronButton, new Color(44, 62, 80));
        customizeButton(displayPatronsButton, new Color(230, 126, 34));
        customizeButton(addTransactionButton, new Color(46, 204, 113));
        customizeButton(checkoutBookButton, new Color(52, 152, 219));
        customizeButton(returnBookButton, new Color(44, 62, 80));
        customizeButton(displayTransactionsButton, new Color(231, 76, 60));

        JButton registerButton = new JButton("Register User");
        customizeButton(registerButton, new Color(155, 89, 182));
        buttonPanel.add(registerButton);

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
        customizeButton(loginButton, new Color(46, 204, 113));
        buttonPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter username:");
                String password = JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter password:");

                if (username != null && password != null) {
                    if (library.authenticateUser(username, password)) {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Login successful!");
                        LibraryManagementApp.this.setButtonsEnabled(true); // Enable buttons after successful login
                    } else {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Login failed. Invalid credentials.");
                    }
                }
            }
        });

        JButton logoutButton = new JButton("Logout");
        customizeButton(logoutButton, new Color(192, 57, 43));
        buttonPanel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibraryManagementApp.this.setButtonsEnabled(false); // Disable buttons on logout
            }
        });

        setButtonsEnabled(false);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Book",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String year = yearField.getText();
                    String isbn = isbnField.getText();

                    if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty() && !isbn.isEmpty()) {
                        if (isValidISBN(isbn)) {
                            Book book = new Book(-1, title, author, year, isbn);
                            library.addBook(book);
                            JOptionPane.showMessageDialog(LibraryManagementApp.this, "Book added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(LibraryManagementApp.this, "Invalid year or ISBN format.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Please fill in all fields.");
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                int result = JOptionPane.showConfirmDialog(null, panel, "Update Book",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String year = yearField.getText();
                    String isbn = isbnField.getText();

                    if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty() && !isbn.isEmpty()) {
                        int selectedBookId = Integer.parseInt(bookIdField.getText());
                        Book updatedBook = new Book(selectedBookId, title, author, year, isbn);

                        try {
                            library.updateBook(updatedBook);
                            JOptionPane.showMessageDialog(LibraryManagementApp.this, "Book updated successfully!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(LibraryManagementApp.this,
                                    "Error updating book: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Please fill in all fields.");
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedBookId = Integer.parseInt(
                            JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter Book ID to Delete:"));

                    try {
                        String deleteSql = "DELETE FROM books WHERE id = ?";
                        PreparedStatement deleteStatement = library.getConnection().prepareStatement(deleteSql);
                        deleteStatement.setInt(1, selectedBookId);
                        int rowsAffected = deleteStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(LibraryManagementApp.this, "Book deleted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(LibraryManagementApp.this,
                                    "Book with ID " + selectedBookId + " not found in the database.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(LibraryManagementApp.this,
                                "Error deleting book: " + ex.getMessage());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Invalid book ID format.");
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Book> books = library.getAllBooks();

                StringBuilder bookList = new StringBuilder();
                for (Book book : books) {
                    bookList.append(book.getInfo()).append("\n\n");
                }

                if (bookList.length() == 0) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "No books available.");
                } else {
                    JTextArea textArea = new JTextArea(bookList.toString());
                    textArea.setEditable(false);
                    // Customize text area appearance

                    textArea.setFont(new Font("Calibri", Font.PLAIN, 16)); // Change font and size
                    textArea.setForeground(Color.BLACK); // Change text color
                    textArea.setBackground(new Color(240, 240, 240)); // Light gray background
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, scrollPane, "Book List",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        addPatronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                JTextField nameField = new JTextField();
                panel.add(nameField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Patron",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();

                    if (!name.isEmpty()) {
                        Patron patron = new Patron(-1, name); // -1 for placeholder ID
                        library.addPatron(patron);
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Patron added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Please fill in all fields.");
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

                int result = JOptionPane.showConfirmDialog(null, panel, "Update Patron",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String newName = newNameField.getText();

                    if (!newName.isEmpty()) {
                        int selectedPatronId = Integer.parseInt(patronIdField.getText());
                        Patron updatedPatron = new Patron(selectedPatronId, newName);

                        library.updatePatron(updatedPatron);
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Patron updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Please fill in all fields.");
                    }
                }
            }
        });

        deletePatronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedPatronId = Integer.parseInt(
                            JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter Patron ID to Delete:"));

                    // Delete patron from the database using selectedPatronId
                    library.deletePatron(selectedPatronId);

                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Patron deleted successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Invalid patron ID format.");
                }
            }
        });

        displayPatronsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Patron> patrons = library.getAllPatrons();

                StringBuilder patronList = new StringBuilder();
                for (Patron patron : patrons) {
                    patronList.append(patron.getInfo()).append("\n\n");
                }

                if (patronList.length() == 0) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "No patrons available.");
                } else {
                    JTextArea textArea = new JTextArea(patronList.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Calibri", Font.PLAIN, 16)); // Change font and size
                    textArea.setForeground(Color.BLACK); // Change text color
                    textArea.setBackground(new Color(240, 240, 240)); // Light gray background
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, scrollPane, "Patron List",
                            JOptionPane.INFORMATION_MESSAGE);
                }
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

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Transaction",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int bookId = Integer.parseInt(bookIdField.getText());
                        int patronId = Integer.parseInt(patronIdField.getText());

                        library.checkoutBook(bookId, patronId);
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Transaction added successfully!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(LibraryManagementApp.this, "Invalid input format.");
                    }
                }
            }
        });
        checkoutBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int bookId = Integer.parseInt(
                            JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter Book ID to Checkout:"));
                    int patronId = Integer.parseInt(
                            JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter Patron ID:"));

                    library.checkoutBook(bookId, patronId);

                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Book checked out successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Invalid input format.");
                }
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int transactionId = Integer.parseInt(
                            JOptionPane.showInputDialog(LibraryManagementApp.this, "Enter Transaction ID to Return:"));

                    library.returnBook(transactionId);

                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Book returned successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "Invalid input format.");
                }
            }
        });

        displayTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Transaction> transactions = library.getAllTransactions();

                StringBuilder transactionList = new StringBuilder();
                for (Transaction transaction : transactions) {
                    if (transaction != null) {
                        System.out.println("Transaction ID: " + transaction.getId());
                        System.out.println("Book ID: " + transaction.getBookId()); // Fetch book ID directly
                        System.out.println("Patron ID: " + transaction.getPatronId()); // Fetch patron ID directly
                        System.out.println("Checkout Date: " + transaction.getCheckoutDate());
                        System.out.println("Due Date: " + transaction.getDueDate());
                        System.out.println();

                        transactionList.append("Transaction ID: ").append(transaction.getId()).append("\n");
                        transactionList.append("Book ID: ").append(transaction.getBookId()).append("\n");

                        transactionList.append("Patron ID: ").append(transaction.getPatronId()).append("\n");

                        transactionList.append("Checkout Date: ").append(transaction.getCheckoutDate()).append("\n");
                        transactionList.append("Due Date: ").append(transaction.getDueDate()).append("\n");
                        transactionList.append("\n");
                    } else {
                        System.out.println("Encountered a null transaction.");
                    }
                }

                if (transactionList.length() == 0) {
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, "No transactions available.");
                } else {
                    JTextArea textArea = new JTextArea(transactionList.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Calibri", Font.PLAIN, 16)); // Change font and size
                    textArea.setForeground(Color.BLACK); // Change text color
                    textArea.setBackground(new Color(240, 240, 240)); // Light gray background
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    JOptionPane.showMessageDialog(LibraryManagementApp.this, scrollPane, "Transaction List",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // JFrame setup
        setTitle("Library Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
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
