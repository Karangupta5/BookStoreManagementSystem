package bookStore.DAO;

import java.util.List;

import bookStore.exception.*;
import bookStore.model.Customer;

public interface CustomerDAO {
	boolean addCustomer(Customer customer) throws InvalidEmailException;
    Customer getCustomerbyId(int customerId);
    boolean deleteCustomer(int customerId);
    List<Customer> getAllCustomer();
}
