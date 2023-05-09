package nl.fontys.sebivenlo.library;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple book object. All objects are Immutable.
 *
 * Important: two books are considered to be equal, when their IDs match.
 *
 * @author Jan Trienes
 */
public class Book {

    /**
     * Enumeration of languages that are available for books.
     */
    public enum Language {

        GERMAN, DUTCH, ENGLISH;
    }

    private final long id;
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final Language language;
    private final short yearOfPublication;

    /**
     * Create book from input.
     *
     * @param id primary key
     * @param title sic
     * @param author sic
     * @param isbn sic
     * @param publisher sic
     * @param language sic
     * @param yearOfPublication sic
     */
    public Book( long id, String title, String author, String isbn,
            String publisher, Language language, int yearOfPublication ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.language = language;
        this.yearOfPublication = (short) yearOfPublication;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (int) ( this.id ^ ( this.id >>> 32 ) );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Book other = (Book) obj;
        return this.id == other.id;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + "\n, title=" + title + "\n, author="
                + author + "\n, isbn=" + isbn + "\n, publisher=" + publisher
                + "\n, language=" + language + "\n, " + yearOfPublication + "\n}";
    }

    /**
     * Static factory from array of string to books.
     * 
     *
     * @param parts used to construct
     * @return a book instance
     * @throws RuntimeException when one of the parts does not meet the field
     * specification of the book, such as where a number is expected, a non
     * number string is provided.
     */
    static Book fromStrings( String[] parts ) {
        long id = Long.parseLong( parts[ 0 ] );
        String title = parts[ 1 ];
        String author = parts[ 2 ];
        String isbn = parts[ 3 ];
        String publisher = parts[ 4 ];
        Book.Language language = Book.Language.valueOf( parts[ 5 ] );
        int yearOfPub = Integer.parseInt( parts[ 6 ] );
        Book b = new Book( id, title, author, isbn, publisher, language,
                yearOfPub );
        return b;
    }

    /**
     * Read a file as a list of books. For this demo, any exceptions are logged.
     * When exceptions occur, an null is return, to fail fast.
     *
     * @param filename to read. The file is read from or relative to the current
     * process directory.
     * @return a list of books.
     * @throws java.net.URISyntaxException when the filename is not proper
     */
    public static List<Book> loadFromFile( String filename ) throws URISyntaxException {
        List<Book> result = null;

        try{
            CSVObjectStream<Book> bookStream = new CSVObjectStream<>(
                    Paths.get( filename ) );
            Predicate<String[]> rowFilter = as -> as.length >= 7 && as[ 0 ]
                    .matches( "^\\d+$" );
            result = bookStream.asList( Book::fromStrings, rowFilter );
        } catch ( IOException ex ) {
            Logger.getLogger( Book.class.getName() )
                    .log( Level.SEVERE, null, ex );
        }
        return result;
    }
}
