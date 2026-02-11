package no.ntnu.book.repository;

import no.ntnu.book.model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class responsible for all database access related to {@link Book}.
 *
 * <p>This class encapsulates all SQL and JDBC logic for the {@code books} table,
 * including creating connections, executing queries, and mapping result sets
 * to {@link Book} objects.</p>
 *
 * <p>The repository follows the Repository pattern and provides a clean API
 * for persistent storage operations such as create, read, update, and delete (CRUD),
 * without exposing SQL details to higher layers.</p>
 *
 * <p>This class does not contain business logic or validation logic; its sole
 * responsibility is data persistence.</p>
 */
public class BookRepository {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    /**
     * Constructs a new BookRepository with the specified database connection parameters.
     *
     * @param dbUrl the JDBC URL of the database
     * @param dbUser the username for the database connection
     * @param dbPassword the password for the database connection
     */
    public BookRepository(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * Creates and returns a new database connection using the configured parameters.
     *
     * @return a new Connection object
     * @throws SQLException if a database access error occurs or the connection parameters are invalid
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a list of all books in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Book> findAll() throws SQLException {
        List<Book> result = new ArrayList<>(); // Make new ArrayList to store the results
        String sql = "SELECT id, title, publication_year, pages FROM books"; // SQL query to select all books from the database

        try (Connection conn = getConnection(); // Try getting a connection to the database
             PreparedStatement stmt = conn.prepareStatement(sql); // Prepare the SQL statement
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { // Iterate over the result set and create Book objects
                result.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("publication_year"),
                        rs.getInt("pages")
                ));
            }
        }
        return result;
    }

    /**
     * Retrieves a book by its id from the database.
     *
     * @param id the id of the book to retrieve
     * @return an Optional containing the book if found, or an empty Optional if not found
     * @throws SQLException if a database access error occurs or the connection parameters are invalid
     */
    public Optional<Book> findById(int id) throws SQLException {
        String sql = "SELECT id, title, publication_year, pages FROM books WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Set the id parameter in the SQL query

            try (ResultSet rs = stmt.executeQuery()) { // Execute the query and get the result set
                if (rs.next()) {  // If a book with the specified id is found, create a Book object and return it wrapped in an Optional
                    return Optional.of(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("publication_year"),
                            rs.getInt("pages")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Inserts a new book into the database and returns the generated id.
     *
     * @param book the book to insert into the database
     * @return the generated id of the inserted book
     * @throws SQLException if a database access error occurs or the connection parameters are invalid
     */
    public int insert(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, publication_year, pages) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getYear());
            stmt.setInt(3, book.getNumberOfPages());

            int affectedRows = stmt.executeUpdate(); // Execute the insert statement and get the number of affected rows
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Insert failed, no ID obtained.");
    }

    /**
     * Updates an existing book in the database with the specified id using the provided book data.
     *
     * @param id the id of the book to update
     * @param book the book data to update the existing book with
     * @return the number of rows affected by the update operation (should be 1 if successful, 0 if no book with the specified id exists)
     * @throws SQLException if a database access error occurs or the connection parameters are invalid
     */
    public int update(int id, Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, publication_year = ?, pages = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getYear());
            stmt.setInt(3, book.getNumberOfPages());
            stmt.setInt(4, id);

            return stmt.executeUpdate(); // Execute the update statement and return the number of affected rows
        }
    }

    /**
     * Deletes a book with the specified id from the database.
     *
     * @param id the id of the book to delete
     * @return the number of rows affected by the delete operation (should be 1 if successful, 0 if no book with the specified id exists)
     * @throws SQLException if a database access error occurs or the connection parameters are invalid
     */
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}
