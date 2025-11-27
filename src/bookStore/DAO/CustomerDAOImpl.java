package bookStore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookStore.model.Customer;
import bookStore.util.DBUtil;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public boolean addCustomer(Customer customer) {
		String query="insert into customers(customer_id,name,email,phone) values(?,?,?,?)";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,customer.getCustomer_id());
			ps.setString(2,customer.getName());
			ps.setString(3,customer.getEmail());
			ps.setString(4,customer.getPhone_No());
			return ps.executeUpdate()>0;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Customer getCustomerbyId(int customerId) {
		String query="select * from customers where customer_id= ?";
		Customer customer=null;
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,customerId);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				customer=new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("email"),rs.getString("phone"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public boolean deleteCustomer(int customerId) {
		String query="delete from customers where customer_id= ?";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,customerId);
			return ps.executeUpdate()>0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> list=new ArrayList<Customer>();
		String query="select * from customers";
		try(Connection con=DBUtil.getConnection();
			Statement ps=con.createStatement();
			ResultSet rs=ps.executeQuery(query)){
			while(rs.next()) {
				Customer customer=new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("email"),rs.getString("phone"));
				list.add(customer);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public boolean exists(int customerId) throws SQLException {
		final Logger logger = Logger.getLogger(CustomerDAO.class.getName());
	    String sql = "SELECT COUNT(*) FROM customers WHERE id = ?";
	    try (Connection con=DBUtil.getConnection();
	    	PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, customerId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	logger.info("Checked existence of customer ID: " + customerId);
	            return rs.getInt(1) > 0; }// If count > 0 → exists
	        } catch (SQLException e) {
	            logger.log(Level.SEVERE, "DB error while checking customer existence", e);
	        }
	        return false;
	}
}
