package bookStore.exception;

import bookStore.app.BookStoreApp;

public class InsufficientStockException extends Exception {
	public InsufficientStockException(String message){
		super(message);
	}
}
