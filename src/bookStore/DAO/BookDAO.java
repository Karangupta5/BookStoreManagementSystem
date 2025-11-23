package bookStore.DAO;

import java.util.*;
import bookStore.model.Book;

public interface BookDAO {
	boolean addBook(Book book);
    Book getBookbyId(int bookId);
    boolean updateBook(Book book);
    boolean deleteBook(int bookId);
    List<Book> getAllBooks();
}
