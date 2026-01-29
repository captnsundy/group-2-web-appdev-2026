package no.ntnu.book.model;

/**
 * Represents an author with an id, first name, last name, and birth year.
 */
public class Author {
    private int id;
    private String firstName;
    private String lastName;
    private int birthYear;

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
    public Author(int id, String firstName, String lastName, int birthYear) {
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
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the author.
     *
     * @param id the new id of the author
     */
    public void setId(int id) {
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
}
