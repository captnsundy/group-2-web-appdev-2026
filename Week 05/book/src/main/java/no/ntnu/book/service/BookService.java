package no.ntnu.book.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import no.ntnu.book.model.Book;
import no.ntnu.book.repository.BookRepository;
import no.ntnu.book.controller.BookController;

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
@Service
public class BookService {

    private final BookRepository repo;

    /**
     * Constructs a new BookService with the specified BookRepository injected by Spring.
     * @param repo the BookRepository to use for data access
     */
    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all books from the database.
     * @return all books in the database
     */
    public Iterable<Book> getAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a book by its id from the database.
     * @param id the id of the book to retrieve
     * @return an Optional containing the book if found, or an empty Optional if not found
     */
    public Optional<Book> getById(int id) {
        return repo.findById(id);
    }

    /**
     * Creates a new book in the database. Validates the book data before insertion.
     * @param book the book to create
     * @return the generated id of the created book
     */
    public int create(Book book) {
        validateBook(book);
        Book saved = repo.save(book);
        return saved.getId();
    }

    /**
     * Updates an existing book in the database. Validates the book data before updating.
     * @param id the id of the book to update
     * @param book the updated book data
     * @return true if the book existed and was updated, false if no book with that id exists
     */
    public boolean update(int id, Book book) {
        if (!repo.existsById(id)) {
            return false;
        }
        validateBook(book);
        book.setId(id);
        repo.save(book);
        return true;
    }

    /**
     * Deletes a book with the specified id from the database.
     * @param id the id of the book to delete
     * @return true if the book existed and was deleted, false otherwise
     */
    public boolean delete(int id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }

    /**
     * Validates the provided book data according to business rules.
     * @param book the book to validate
     * @throws IllegalArgumentException if the book data is invalid
     */
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Book title cannot be null/blank.");
        }
        if (book.getYear() < 0) {
            throw new IllegalArgumentException("Year cannot be negative.");
        }
        if (book.getNumberOfPages() < 0) {
            throw new IllegalArgumentException("Number of pages cannot be negative.");
        }
    }
}
