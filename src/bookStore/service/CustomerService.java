package bookStore.service;

import java.util.List;

import bookStore.exception.CustomerNotFoundException;
import bookStore.exception.InvalidInputException;
import bookStore.model.Customer;

public interface CustomerService {
	
	void addCustomer(Customer customer)throws InvalidInputException;

    void deleteCustomer(int customerId) throws CustomerNotFoundException;

    Customer getCustomerById(int customerId) throws CustomerNotFoundException;

    List<Customer> getAllCustomers();
}
