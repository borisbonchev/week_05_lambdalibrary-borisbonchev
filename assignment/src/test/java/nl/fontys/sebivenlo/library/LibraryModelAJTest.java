package nl.fontys.sebivenlo.library;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Use the AssertJ Library to do some tests.
 *
 * @author Jan Trienes
 */
public class LibraryModelAJTest {

    private LibraryModel modelInstance;
    private List<Book> books;

    @BeforeEach
    void setUp() throws Exception {
        books = new ArrayList<>();
        books = Book.loadFromFile( "library.csv" );
        modelInstance = new DefaultLibrary( books );
    }

    /**
     * Use the AssertJ containsExacltyElementsOf.
     *
     * Test if all elements in books are available in the same order as in the
     * imported books list.
     */
    //@Disabled( "Think TDD" )
    @Test
    void testGetBooks() {
        //TODO implement testGetBooks
        assertThat(modelInstance.getBooks()).as("Contains books in the correct order.")
                .containsExactlyElementsOf(books);
    }

    //@Disabled( "Think TDD" )
    @Test
    void testBooksMatchSearchTerm() {
        //TODO write testBooksMatchSearchTerm
//        System.out.println(modelInstance.booksMatchSearchTerm("Martin Fowler"));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(modelInstance.booksMatchSearchTerm("Martin Fowler")).contains(books.get(1));
            softly.assertThat(modelInstance.booksMatchSearchTerm("Martin Fowler")).contains(books.get(10));
        });
    }

    //@Disabled( "Think TDD" )
    @Test
    void testAuthorsMatchSearchTerm() {
        //TODO write test authorsMatchSearchTerm
        System.out.println(modelInstance.authorsMatchSearchTerm("Robert C. M"));
        assertThat(modelInstance.authorsMatchSearchTerm("Robert C. Martin")).as("Should return the author")
                        .contains(books.get(8).getAuthor());
    }

    /**
     * Use assertj's
     * {@code Assertions.assertThat( resultBooks ).containsExactlyInAnyOrder(....)}.
     */
    //@Disabled( "Think TDD" )
    @Test
    void testBooksMatchPredicate() {
        //TODO write test for booksMatchPredicate
        assertThat(modelInstance.booksMatchPredicate(book -> book.getAuthor().equals("Brian Goetz")))
                .as("Should return an instance of the requested book.")
                        .containsExactlyInAnyOrder(books.get(3));
    }

    /**
     * Test with a few books. Use default junit asserts.
     */
    //@Disabled( "Think TDD" )
    @ParameterizedTest
    @CsvSource( value = {
        "1,'Head First Design Patterns'",
        "12,'Computer Networks and Internets: With Internet Applications'",
        "14,'Practical Unit Testing with JUnit and Mockito'" }
    )
    void testGetBookById( int bookId, String title) {
        //TODO write test for getBookById.
        assertThat(modelInstance.getBookById(bookId)).as("Should return the corresponding book")
                .isEqualTo(books.get(bookId - 1));
    }

    /**
     * Write test to see that a mutating (e.g. remove) operation throws an
     * UnsupportedOperationException using
     * {@code AssertJK.assertThatThrownBy(ThrowableAssert.ThrowingCallable)}.
     *
     */
    //@Disabled( "Think TDD" )
    @Test
    void testCollectionsIsUnmodifiable() {
        List<Book> list = modelInstance.getBooks();
        Assertions.assertThatThrownBy( () -> list.remove( 0 ) ).isInstanceOf(
                UnsupportedOperationException.class );
    }

    //@Disabled( "Think TDD" )
    @Test
    void testToString() {
        System.out.println( modelInstance );
    }
        /**
     * Helper method to get a book from the standard set.
     *
     * @param b index in list
     * @return the chosen book
     */
    Book book( int b ) {
        return books.get( b );
    }

    /**
     * Gets books with given id into array. Test helper.
     *
     * @param ids  of books
     * @return the array containing the selected books.
     */
    Book[] books( int... ids ) {
        Book[] result = new Book[ ids.length ];
        int b = 0;
        for ( int i : ids ) {
            result[ b++ ] = book( i );
        }
        return result;
    }

}
