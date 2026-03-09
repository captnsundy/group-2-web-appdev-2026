package no.ntnu.book.repository;

import no.ntnu.book.model.Book;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Book} entities.
 */
public interface BookRepository extends CrudRepository<Book, Integer> {
    Iterable<Book> findByYearGreaterThan(int minYear);
}
