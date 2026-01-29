package no.ntnu.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import no.ntnu.book.model.Book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Controller class for managing books.
 *
 * <p>Provides REST endpoints to get all books, get a book by id, and add a new book.</p>
 */
@RestController
public class BookController {
    private List<Book> books = new ArrayList<>();

    /**
     * Constructs a new BookController and initializes sample book data.
     */
    public BookController() {
        initializeData();
    }

    /**
     * Initializes some sample book data.
     *
     * <p>Example books:
     * <ul>
     * <li>"The Great Gatsby" (1925, 218 pages)</li>
     * <li>"To Kill a Mockingbird" (1960, 281 pages)</li>
     * <li>"1984" (1949, 328 pages)</li>
     * </ul>
     */
    private void initializeData() {
        books.add(new Book(1, "The Great Gatsby", 1925, 218));
        books.add(new Book(2, "To Kill a Mockingbird", 1960, 281));
        books.add(new Book(3, "1984", 1949, 328));
    }

    /**
     * Returns REST endpoint to get all books, mapped to /books.
     *
     * <p>Example: GET http://localhost:8080/books</p>
     */
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return books;
    }
    
    /**
     * Returns REST endpoint to get a book by its id, mapped to /books/{id}.
     *
     * <p>Example: GET http://localhost:8080/books/1</p>
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        for (Book book : books) { // Iterate through the list of books
            if (book.getId() == id) { // Check if the book's id matches the requested id
                return ResponseEntity.ok(book); 
                // Return the book wrapped in a ResponseEntity with HTTP 200 OK
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        // Or return HTTP 404 Not Found if not found
    }

    /**
     * Returns REST endpoint to add a new book, mapped to /books.
     *
     * <p>Example: POST http://localhost:8080/books</p>
     */
    @PostMapping("/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        // Simple validation: check for null title
        if (book.getTitle() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error, book title cannot be null");
        }
        // Check for duplicate book id
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error, book with the same ID already exists");
            }
        }
        books.add(book);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Book added successfully!");
    }

    /**
     * Returns REST endpoint to update an existing book, mapped to /books/{id}.
     * @param id the id of the book to update
     * @param updatedBook the updated book data
     * @return ResponseEntity with status and message
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(
        @PathVariable int id,
        @RequestBody Book updatedBook) {

        // Find the book by id and update its details
        for (Book book : books) {
            if (book.getId() == id) {
            book.setTitle(updatedBook.getTitle());
            book.setYear(updatedBook.getYear());
            book.setNumberOfPages(updatedBook.getNumberOfPages());

            return ResponseEntity.ok("Book updated");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Book not found");
         // Return HTTP 404 Not Found if book not found
    }

    /**
     * Deletes a book by its id, mapped to /books/{id}.
     * @param id the id of the book to delete
     * @return ResponseEntity with status and message
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        for (Book book : books) {
            // Check if the book with the given id exists
            if (book.getId() == id) {
            books.remove(book);
            return ResponseEntity.ok("Book deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Book not found");
        // Return HTTP 404 Not Found if book not found
    }
}
