package bookStore.service;

import java.util.List;
import bookStore.DAO.*;

import bookStore.exception.CustomerNotFoundException;
import bookStore.exception.InvalidInputException;
import bookStore.model.Customer;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerDAO dao=new CustomerDAOImpl();

	@Override
	public void addCustomer(Customer customer) throws InvalidInputException {
		if (customer.getName() == null || customer.getName().trim().isEmpty())
	        throw new InvalidInputException("Customer name cannot be empty");

	    if (!customer.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
	        throw new InvalidInputException("Invalid email format");

	    if (customer.getPhone_No().length() != 10)
	        throw new InvalidInputException("Phone number must be 10 digits");
		try {
			dao.addCustomer(customer);
			System.out.println("Customer added successfully");
		} catch(Exception e) {
			System.out.println("Error Adding Customer: "+ e.getMessage());
		}
	}
	
	@Override
	public void deleteCustomer(int customerId) throws CustomerNotFoundException {
		if(dao.getCustomerbyId(customerId)==null) {
			throw new CustomerNotFoundException("Customer Not Found ID: " + customerId);
		}
		try {
			dao.deleteCustomer(customerId);
			System.out.println("Customer deleted successfully");
		} catch(Exception e) {
			System.out.println("Error Deleting Customer: "+ e.getMessage());
		}
	}

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		Customer customer=dao.getCustomerbyId(customerId);
		if(customer==null) {
			throw new CustomerNotFoundException("Customer Not Found ID: " + customerId);
		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return dao.getAllCustomer();
	}

}
