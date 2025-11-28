package bookStore.service;

import java.util.List;
import bookStore.DAO.*;
import bookStore.exception.BookNotFoundException;
import bookStore.exception.InvalidInputException;
import bookStore.model.Book;

public class BookServiceImpl implements BookService {
	
	private BookDAO dao=new BookDAOImpl();

	@Override
	public void addBook(Book book) throws InvalidInputException{
		if (book.getPrice() <= 0)
	        throw new InvalidInputException("Price must be greater than 0");
	    if (book.getStock() < 0)
	        throw new InvalidInputException("Stock cannot be negative");
		try {
			dao.addBook(book);
			System.out.println("Book Added Successfully");
		} catch(Exception e) {
			System.out.println("Error Adding Book " +e.getMessage());
		}
	}

	@Override
	public Book searchById(int id) throws BookNotFoundException {
		Book book=dao.getBookbyId(id);
		if(book==null) throw new BookNotFoundException("Book Not Found ID: "+id);
		return book;
	}

	@Override
	public void updateBook(Book book) throws BookNotFoundException {
		if(dao.getBookbyId(book.getBookId())==null) throw new BookNotFoundException("Book Not Found ID: "+ book.getBookId());
		try {
			dao.updateBook(book);
			System.out.println("Book Updated Successfully");
		} catch(Exception e) {
			System.out.println("Error updating Book "+e.getMessage());
		}
	}

	@Override
	public void deleteBook(int id) throws BookNotFoundException {
		if(dao.getBookbyId(id)==null) throw new BookNotFoundException("Book Not Found ID: "+ id);
		try {
			dao.deleteBook(id);
			System.out.println("Book deleted successfully");
		} catch(Exception e) {
			System.out.println("Error Deleting Book "+e.getMessage());
		}
	}

	@Override
	public List<Book> viewAllBooks() {
		return dao.getAllBooks();
	}

	@Override
	public void updateStock(int id,int updatedStock) throws BookNotFoundException, InvalidInputException {
		if(dao.getBookbyId(id)==null) throw new BookNotFoundException("Book Not Found ID: "+ id);
		if(updatedStock<0) throw new InvalidInputException("Stock can not be negative");
		try {
			dao.updateStock(id,updatedStock);
			System.out.println("Stock updated successfully");
		}catch(Exception e) {
			System.out.println("Error Updating Book "+e.getMessage());
		}
	}

}
