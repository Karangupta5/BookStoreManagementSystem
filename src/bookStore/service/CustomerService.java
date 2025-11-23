package bookStore.service;

import java.util.List;
import bookStore.DAO.CustomerDAO;
import bookStore.DAO.CustomerDAOImpl;
import bookStore.model.Customer;

public class CustomerService {
	CustomerDAO dao=new CustomerDAOImpl();

	public void addBook(int customer_id,String name,String email,long phone) {
		dao.addCustomer(new Customer(customer_id,name,email,phone));
	}
	public List<Customer> viewAllBooks() {
		return dao.getAllCustomer();
	}
	public Customer searchById(int id) {
		return dao.getCustomerbyId(id);
	}
	public boolean deleteCustomer(int id) {
		return dao.deleteCustomer(id);
	}
}
