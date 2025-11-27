package bookStore.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookStore.DAO.*;
import bookStore.exception.InsufficientStockException;
import bookStore.exception.InvalidInputException;
import bookStore.model.Order;
import bookStore.model.OrderItem;

public class OrderServiceImpl implements OrderService {
	
	OrderDAO dao=new OrderDAOImpl();
	CustomerDAO cusDao=new CustomerDAOImpl();
	BookDAO bookDao=new BookDAOImpl();

	@Override
	public int placeOrder(Order order) throws SQLException,InsufficientStockException, InvalidInputException {
		int ans=0;
		final Logger logger = Logger.getLogger(OrderService.class.getName());
		   if (!cusDao.exists(order.getCustomer_id())) {
			   logger.warning("Invalid customer ID: " + order.getCustomer_id());
		        throw new InvalidInputException("Customer does not exist!");}

		    if (order.getOrders() == null || order.getOrders().isEmpty()) {
		    	logger.warning("There is no Item in the order " );
		    	throw new InvalidInputException("Order must have at least one item");}

		    for (OrderItem item : order.getOrders()) {

		        if (!bookDao.exists(item.getBook_id())) {
		        	logger.warning("Invalid Book ID: " + item.getBook_id());
		            throw new InvalidInputException("Book ID " + item.getBook_id() + " does not exist!");}

		        if (item.getQuantity() <= 0)
		            throw new InvalidInputException("Quantity must be > 0 for Book ID " + item.getBook_id());
		    }
		for(Order ord:dao.getOrdersByCustomer(order.getCustomer_id())) {
		for (OrderItem item :ord.getOrders()) {
	        int bookId = item.getBook_id();
	        int requestedQty = item.getQuantity();
	        BookDAO bkd=new BookDAOImpl();
	        int availableStock = bkd.getBookStock(bookId);
	        if (requestedQty > availableStock) {
	            throw new InsufficientStockException(
	                "Not enough stock for Book ID " + bookId +
	                ". Available: " + availableStock +
	                ", Requested: " + requestedQty
	            );
	        }
	    }
		}
		ans=dao.insertOrder(order);
		logger.info("Order placed successfully for customer ID: " + order.getCustomer_id());
		return ans;
	}

	@Override
	public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
		return dao.getOrdersByCustomer(customerId);
	}
	
	@Override
	public void printOrderHistory(List<Order> orders) {
	    for (Order order : orders) {
	        System.out.println("\nOrder ID: " + order.getOrder_id());
	        System.out.println("Date: " + order.getOrder_date());
	        System.out.println("Total Amount: " + order.getTotalAmount());
	        System.out.println("Items:");
	        System.out.println("----------------------------------------");

	        for (OrderItem item : order.getOrders()) {
	            System.out.println("Book ID: " + item.getBook_id()
	                    + " | Qty: " + item.getQuantity()
	                    + " | Price: " + item.getUnit_price()
	                    + " | Line Total: " + item.getQuantity() * item.getUnit_price());
	        }

	        System.out.println("----------------------------------------");
	    }
	}

}
