package nl.fontys.sebivenlo.fxlambdalibrary;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import nl.fontys.sebivenlo.library.Book;
import nl.fontys.sebivenlo.library.DefaultLibrary;
import nl.fontys.sebivenlo.library.LibraryModel;

public class FXMLController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label label;

    @FXML
    private TextField searchTerm;

    @FXML
    TableView<Book> tv;

    LibraryModel library;
    ObservableList<Book> bookList = javafx.collections.FXCollections
            .observableArrayList();

    public FXMLController() throws URISyntaxException {
        library = new DefaultLibrary( Book.loadFromFile( "library.csv" ) );
    }

    @Override
    public void initialize( URL url, ResourceBundle rb ) {
        label.setText( "Search Term" );
        bookList.addAll( library.getBooks() );
        defineTable();
        tv.setItems( bookList );
        AnchorPane.setTopAnchor( tv, 40.0 );
        AnchorPane.setRightAnchor( tv, 0.0 );
        AnchorPane.setBottomAnchor( tv, 0.0 );
        AnchorPane.setLeftAnchor( tv, 0.0 );
        searchTerm.setText( "" );
        searchTerm.textProperty().addListener( this::searcher );

    }

    void searcher( ObservableValue<? extends String> t, String ov, String nv ) {
        bookList.clear();
        if ( nv.length() == 0 ) {
            bookList.addAll( library.getBooks() );
        } else {
            bookList.addAll( library.booksMatchSearchTerm( nv ) );
        }
    }

    void defineTable() throws SecurityException {
        Field[] declaredFields = Book.class.getDeclaredFields();
        for ( Field declaredField : declaredFields ) {
            // Class<?> cType = declaredField.getType();
            String clsName = declaredField.getName();
            TableColumn<Book, Object> tc = new TableColumn<>( clsName );
            tc.setCellValueFactory( new PropertyValueFactory<>( clsName ) );
            tv.getColumns().add( tc );
        }
    }
}
