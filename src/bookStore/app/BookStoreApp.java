package bookStore.app;

import bookStore.service.BookService;

public class BookStoreApp {

	public static void main(String[] args) {
		BookService bs=new BookService();
		bs.addBook(1,"Merchant Of Venice","W.Shakespeer",750,50);
		bs.addBook(2,"Julies Caesar","W.Shakespeer",1150,100);
		bs.addBook(3,"Romeo Juliet","R.Robert",850,75);
		System.out.println(bs.viewAllBooks());

	}

}
