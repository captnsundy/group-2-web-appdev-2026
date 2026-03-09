package no.ntnu.book.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.book.model.Book;
import no.ntnu.book.service.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 * <p>The controller interacts with the {@link BookService} to perform business logic and data access operations,
 * and handles HTTP requests and responses. This way, we make sure to keep the controller focused on request handling
 * and delegate business logic to the service layer. Thus we achieve a clean separation of concerns and
 * maintain cohesion in our application design.</p>
 */
@RestController
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService service;

    /**
     * Constructs a new BookController with the provided BookService injected by Spring.
     *
     * @param service the BookService to use for business logic and data access
     */
    public BookController(BookService service) {
        this.service = service;
    }

    /**
     * Returns REST endpoint to get all books, mapped to /books.
     *
     * <p>Example: GET http://localhost:8080/books</p>
     */
    @GetMapping("/books")
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        logger.info("Getting all books");
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Returns REST endpoint to get a book by its id, mapped to /books/{id}.
     *
     * <p>Example: GET http://localhost:8080/books/1</p>
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        logger.info("Getting book with id: {}", id);
        Optional<Book> book = service.getById(id);
        return book.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Returns REST endpoint to add a new book, mapped to /books.
     *
     * <p>Example: POST http://localhost:8080/books</p>
     */
    @Operation(
        summary = "Add a new book",
        description = "Adds a new book to the collection"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Book added successfully",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input",
            content = @Content
        )
    })
    @PostMapping("/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        logger.info("Adding new book: {}", book.getTitle());
        try {
            int newId = service.create(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(newId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Returns REST endpoint to update an existing book, mapped to /books/{id}.
     * @param id the id of the book to update
     * @param updatedBook the updated book data
     * @return ResponseEntity with status and message
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        logger.info("Updating book with id: {}", id);
        try {
            boolean updated = service.update(id, updatedBook);
            if (!updated) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error! Book with id " + id + " not found");
            }
            return ResponseEntity.ok("Book updated successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Deletes a book by its id, mapped to /books/{id}.
     * @param id the id of the book to delete
     * @return ResponseEntity with status and message
     */
    @Operation(hidden = true) // Hide from Swagger documentation
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        logger.info("Deleting book with id: {}", id);
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error! Book with id " + id + " not found");
        }
        return ResponseEntity.ok("Book deleted");
    }
}
