package bookStore.service;

import java.util.List;
import bookStore.DAO.*;
import bookStore.model.Book;

public class BookService {
	BookDAO dao=new BookDAOImpl();

	public void addBook(int book_id,String title,String author,double price,int stock) {
		dao.addBook(new Book(book_id,title,author,price,stock));
	}
	public List<Book> viewAllBooks() {
		return dao.getAllBooks();
	}
	public Book searchById(int id) {
		return dao.getBookbyId(id);
	}
	public boolean updateBook(int id,double newPrice,int newStock) {
		return dao.updateBook(new Book(id,dao.getBookbyId(id).getTitle(),dao.getBookbyId(id).getAuthor(),newPrice,newStock));
	}
	public boolean deleteBook(int id) {
		return dao.deleteBook(id);
	}
}
