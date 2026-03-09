package no.ntnu.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a book with an id, title, publication year, and number of pages.
 */
@Entity
public class Book {
    @Schema(description = "Unique ID of the book")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Title of the book")
    private String title;

    @Schema(description = "The year when the book was published")
    private int year;

    @Schema(description = "The number of pages in the book")
    private int numberOfPages;

    @Schema(description = "The authors of the book")
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    @Schema(description = "Tags associated with the book")
    @ElementCollection
    @CollectionTable(name = "book_tag", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
    
    /**
     * Default constructor that creates an empty Book object.
     *
     * <p>Needed for JSON deserialization in frameworks like Spring Boot,
     * which requires it for '@RequestBody' parameters.</p>
     */
    public Book() {
        // Default constructor
    }

    /**
     * Constructs a new Book with the specified id, title, year, and number of pages.
     * @param id the id of the book
     * @param title the title of the book
     * @param year the publication year of the book
     * @param numberOfPages the number of pages in the book
     */
    public Book(Integer id, String title, int year, int numberOfPages) {
        this.id = id; // this to make sure we refer to the instance variable, not the parameter
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
    }

    /**
     * Returns the id of the book.
     *
     * @return the id of the book
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the book.
     *
     * @param id the new id of the book
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the publication year of the book.
     *
     * @return the publication year of the book
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the publication year of the book.
     *
     * @param year the new publication year of the book
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the number of pages in the book.
     *
     * @return the number of pages in the book
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book.
     *
     * @param numberOfPages the new number of pages in the book
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * Returns the authors of the book.
     *
     * @return the authors of the book
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the authors of the book.
     *
     * @param authors the new authors of the book
     */
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    /**
     * Returns the tags associated with the book.
     *
     * @return the tags associated with the book
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with the book.
     *
     * @param tags the new tags associated with the book
     */
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
