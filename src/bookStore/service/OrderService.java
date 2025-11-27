package bookStore.service;

import java.sql.SQLException;
import java.util.List;

import bookStore.exception.InsufficientStockException;
import bookStore.exception.InvalidInputException;
import bookStore.model.Order;

public interface OrderService {

    public int placeOrder(Order order) throws SQLException,InsufficientStockException,InvalidInputException;

    public List<Order> getOrdersByCustomer(int customerId) throws SQLException;
    
    public void printOrderHistory(List<Order> orders);
}
