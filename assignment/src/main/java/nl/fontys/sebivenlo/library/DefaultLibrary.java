package nl.fontys.sebivenlo.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * DefaultLibrary is the central store of books.
 *
 * The inventory can be used to retrieve several views on the books within the
 * library. Some rudimentary search operations are supplied as well.
 *
 * The library is implemented as a singleton.
 *
 * @author Jan Trienes
 */
public class DefaultLibrary implements LibraryModel {

    /**
     * The only instance of this library.
     */
    private final List<Book> books;

    /**
     * Dummy book returned if no book is found.
     */
    public static final Book NULL_OBJECT_BOOK
            = new Book( 0, "Null object", "", "", "", Book.Language.ENGLISH, -1 );

    /**
     * The constructor loads the library catalogue file.
     *
     * @param books to add in this constructor.
     */
    public DefaultLibrary( List<Book> books ) {
        this.books = books;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Book> getBooks() {
        //TODO make unmodifiable
        return Collections.unmodifiableList(books);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> booksMatchSearchTerm( String searchTerm ) {
        //TODO implement booksmatch search term
        return books.stream()
                .filter(b -> b.getAuthor().matches("(?i).*" + searchTerm + ".*") ||
                        b.getTitle().matches("(?i).*" + searchTerm + ".*") ||
                        b.getPublisher().matches("(?i).*" + searchTerm + ".*"))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> authorsMatchSearchTerm( String searchTerm ) {
        //TODO implement authorsMatchSearchTerm
        return books.stream()
                .map(b -> b.getAuthor())
                .distinct()
                .filter(a -> a.matches("(?i).*" + searchTerm + ".*"))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book getBookById( long id ) {
        //TODO hint: if no book is found, its fine to return the NULL_OBJECT_BOOK
        AtomicReference<Book> existence = new AtomicReference<>(NULL_OBJECT_BOOK);
        books.stream()
                .forEach(b -> {
                    if (b.getId() == id) {
                        existence.set(b);
                    }
                });
        return existence.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> booksMatchPredicate(
            Predicate<? super Book> searchPredicate ) {
        //TODO implement booksMatchPredicate
        return books.stream()
                .filter(searchPredicate)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for ( Book book : books ) {
            sb.append( book.toString() );
            sb.append( "\n" );
        }

        return sb.toString();
    }
}
