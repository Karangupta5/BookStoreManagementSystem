package bookStore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookStore.model.Customer;
import bookStore.util.DBUtil;
import java.util.*;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public boolean addCustomer(Customer customer) {
		String query="insert into customer(customer_id,name,email,phone) values(?,?,?,?)";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,customer.getCustomer_id());
			ps.setString(2,customer.getName());
			ps.setString(3,customer.getEmail());
			ps.setLong(4,customer.getPhone_No());
			return ps.executeUpdate()>0;
		} catch(SQLException e || InvalidEmailException e) {
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
				customer=new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("email"),rs.getLong("phone"));
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
				Customer customer=new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("email"),rs.getLong("phone"));
				list.add(customer);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
