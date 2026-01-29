package no.ntnu.book.model;

/**
 * Represents a book with an id, title, publication year, and number of pages.
 */
public class Book {
    private int id;
    private String title;
    private int year;
    private int numberOfPages;
    
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
    public Book(int id, String title, int year, int numberOfPages) {
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
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the book.
     *
     * @param id the new id of the book
     */
    public void setId(int id) {
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
}
