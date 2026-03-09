package no.ntnu.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an author with an id, first name, last name, and birth year.
 */
@Entity
public class Author {
    @Schema(description = "Unique ID of the author")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "First name of the author")
    private String firstName;

    @Schema(description = "Last name of the author")
    private String lastName;

    @Schema(description = "Birth year of the author")
    private int birthYear;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = @JoinColumn(name = "author_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    /**
     * Default constructor that creates an empty Author object.
     */
    public Author() {
        // Default constructor
    }

    /**
     * Constructs a new Author with the specified id, first name, last name, and birth year.
     * @param id the id of the author
     * @param firstName the first name of the author
     * @param lastName the last name of the author
     * @param birthYear the birth year of the author
     */
    public Author(Integer id, String firstName, String lastName, int birthYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    /**
     * Returns the id of the author.
     *
     * @return the id of the author
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the author.
     *
     * @param id the new id of the author
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the first name of the author.
     *
     * @return the first name of the author
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     *
     * @param firstName the new first name of the author
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the author.
     *
     * @return the last name of the author
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     *
     * @param lastName the new last name of the author
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the birth year of the author.
     *
     * @return the birth year of the author
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Sets the birth year of the author.
     *
     * @param birthYear the new birth year of the author
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
