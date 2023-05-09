package nl.fontys.sebivenlo.library;

import java.util.List;
import java.util.function.Predicate;

/**
 * Base operations for a library.
 *
 * All search operations are performed in such a way, that they are not case
 * sensitive.
 *
 * @author Jan Trienes
 */
public interface LibraryModel {

    /**
     * Returns a full view over all books within the library.
     *
     * The returned list must be un modifiable.
     *
     * @return Unmodifiable list of books
     */
    List<Book> getBooks();

    /**
     * Retrieves all books which match the provided string.
     *
     * Considered parameters during search are the author, the title and the
     * publisher.
     *
     * @param searchTerm the search phrase
     * @return Unmodifiable list of books that match the search phrase
     */
    List<Book> booksMatchSearchTerm( String searchTerm );

    /**
     * Retrieves all authors which have written books stored in the library.
     *
     * The returned list does not contain any author twice, although the author
     * might have multiple books in the library.
     *
     * @param searchTerm the search phrase
     * @return all authors, that match the search phrase
     */
    List<String> authorsMatchSearchTerm( String searchTerm );

    /**
     * Retrieves all books, which match an arbritrary predicate.
     *
     * The predicate can be handed over as a lambda expression.
     *
     * @param searchPredicate the search predicate
     * @return Unmodifiable list of books that match the search predicate
     */
    List<Book> booksMatchPredicate( Predicate<? super Book> searchPredicate );

    /**
     * Retrieve only the book, which matches the supplied id.
     *
     * If no book is found, a book with ID=0 is returned instead of throwing an
     * exception or returning null.
     *
     * @param id the id to search for
     * @return the book that matches the id
     */
    Book getBookById( long id );

}
