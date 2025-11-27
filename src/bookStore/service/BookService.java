package bookStore.service;

import java.util.List;

import bookStore.exception.BookNotFoundException;
import bookStore.exception.InvalidInputException;
import bookStore.model.Book;

public interface BookService {
	
	public void addBook(Book book) throws InvalidInputException;
	public Book searchById(int id) throws BookNotFoundException;
	public void updateBook(Book book) throws BookNotFoundException; 
	public void deleteBook(int id) throws BookNotFoundException;
	public List<Book> viewAllBooks();
}
