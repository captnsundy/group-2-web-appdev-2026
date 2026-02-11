package no.ntnu.book.service;

import no.ntnu.book.model.Book;
import no.ntnu.book.repository.BookRepository;
import no.ntnu.book.controller.BookController;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service class containing business logic related to {@link Book} entities.
 *
 * <p>This class acts as an intermediary between the controller layer {@link BookController} and the
 * repository layer {@link BookRepository}. It coordinates repository calls and enforces business
 * rules and validation.</p>
 *
 * <p>The service layer ensures that controllers remain free of business logic
 * and that repositories remain free of validation or application-specific rules.</p>
 */

public class BookService {

    private final BookRepository repo;

    /**
     * Constructs a new BookService with the specified BookRepository.
     * @param repo the BookRepository to use for data access
     */
    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all books from the database.
     * @return a list of all books in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Book> getAll() throws SQLException {
        return repo.findAll();
    }

    /**
     * Retrieves a book by its id from the database.
     * @param id the id of the book to retrieve
     * @return an Optional containing the book if found, or an empty Optional if not found
     * @throws SQLException if a database access error occurs
     */
    public Optional<Book> getById(int id) throws SQLException {
        return repo.findById(id);
    }

    /**
     * Creates a new book in the database using the provided book data. Validates the book data before insertion.
     * @param book the book to create in the database
     * @return the generated id of the created book
     * @throws SQLException if a database access error occurs
     */
    public int create(Book book) throws SQLException {
        validateBook(book);
        return repo.insert(book);
    }

    /**
     * Updates an existing book in the database with the specified id using the provided book data.
     * Validates the book data before updating.
     * @param id the id of the book to update
     * @param book the book data to update the existing book with
     * @return true if the update was successful (i.e., a book with the specified id existed and was updated), false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean update(int id, Book book) throws SQLException {
        validateBook(book);
        return repo.update(id, book) > 0;
    }

    /**
     * Deletes a book with the specified id from the database.
     *
     * @param id the id of the book to delete
     * @return true if the delete was successful (i.e., a book with the specified id existed and was deleted), false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean delete(int id) throws SQLException {
        return repo.delete(id) > 0;
    }

    /**
     * Validates the provided book data according to business rules.
     * @param book the book to validate
     * @throws IllegalArgumentException if the book data is invalid according to business rules
     */
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Book title cannot be null/blank.");
        }
        // optional: protect against nonsense values
        if (book.getYear() < 0) {
            throw new IllegalArgumentException("Year cannot be negative.");
        }
        if (book.getNumberOfPages() < 0) {
            throw new IllegalArgumentException("Number of pages cannot be negative.");
        }
    }
}
