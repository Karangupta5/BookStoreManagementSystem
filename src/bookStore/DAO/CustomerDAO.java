package bookStore.DAO;

import java.sql.SQLException;
import java.util.List;
import bookStore.model.Customer;

public interface CustomerDAO {
	boolean addCustomer(Customer customer);
    Customer getCustomerbyId(int customerId);
    boolean deleteCustomer(int customerId);
    List<Customer> getAllCustomer();
    public boolean exists(int customerId) throws SQLException;
    
}
