package bookStore.app;

import bookStore.model.*;
import bookStore.service.*;
import java.sql.SQLException;
import bookStore.exception.InsufficientStockException;
import bookStore.exception.InvalidInputException;

public class BookStoreApp {

	public static void main(String[] args) {
		BookService bs=new BookServiceImpl();
//		bs.addBook(new Book(1,"Merchant Of Venice","W.Shakespeer",750.0,50));
//		bs.addBook(new Book(2,"Julies Caesar","W.Shakespeer",1150.0,100));
//		bs.addBook(new Book(3,"Romeo Juliet","R.Robert",850.0,75));
//		bs.addBook(new Book(4,"Java Fundamentals","Justin Dwivedi",350.0,3));
		System.out.println(bs.viewAllBooks() +"\n");
//		
		CustomerService cs=new CustomerServiceImpl();
//		cs.addCustomer(new Customer(1,"Karan","karan@gmail.com",7983389852L));
//		cs.addCustomer(new Customer(2,"Ritik","ritik@gmail.com",7854123558L));
		System.out.println(cs.getAllCustomers() +"\n");
		
		  Order order = new Order(1); // customer_id = 1

		  //order.addItem(new OrderItem(1,4,5,350.0));
		  order.addItem(new OrderItem(2,1,40,750.0));

        OrderService os=new OrderServiceImpl();
        try {
        	//System.out.println(orders);
        	os.placeOrder(order);
			os.printOrderHistory(os.getOrdersByCustomer(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(InsufficientStockException e) {
			e.printStackTrace();
		} catch(InvalidInputException e) {
			e.printStackTrace();
		}
	}

}
