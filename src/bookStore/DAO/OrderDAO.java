package bookStore.DAO;

import java.sql.SQLException;
import java.util.List;

import bookStore.model.Order;

public interface OrderDAO {
	int insertOrder(Order order);
	List<Order> getOrdersByCustomer(int customer_id);
}
