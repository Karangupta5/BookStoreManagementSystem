package bookStore.DAO;

import java.sql.SQLException;
import java.util.*;
import bookStore.model.Book;

public interface BookDAO {
	boolean addBook(Book book);
    Book getBookbyId(int bookId);
    boolean updateBook(Book book);
    boolean deleteBook(int bookId);
    int getStock(int bookId);
    int getBookStock(int bookId) throws SQLException;
    List<Book> getAllBooks();
    public boolean exists(int bookId) throws SQLException;
    boolean updateStock(int id,int stock) throws SQLException;
}
