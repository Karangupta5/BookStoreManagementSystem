package bookStore.DAO;

import java.sql.*;
import java.util.*;

import bookStore.model.Order;
import bookStore.model.OrderItem;
import bookStore.util.DBUtil;

public class OrderDAOImpl implements OrderDAO {

	@Override
	public int insertOrder(Order order) {
		Connection con=null;
		PreparedStatement psOrder=null;
		PreparedStatement psItem=null;
		PreparedStatement psStock=null;
		ResultSet rs=null;
		int orderId=0;
		
		try {
			con=DBUtil.getConnection();
			con.setAutoCommit(false);
			String orderQuery="insert into orders(order_id,customer_id,order_date,total_amount) values(?,?,?,?)";
			psOrder=con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
			psOrder.setInt(1,order.getOrder_id());
			psOrder.setInt(2,order.getCustomer_id());
			psOrder.setDate(3,new java.sql.Date(System.currentTimeMillis()));
			psOrder.setDouble(4,order.getTotal_amount());
			if(psOrder.executeUpdate()>0) {
			rs=psOrder.getGeneratedKeys();
			if(rs.next()) { orderId=rs.getInt(1);
			order.setOrder_id(orderId);
			}}
			//insert into order_items
			String Order_ItemQuery="insert into order_items(order_id,book_id,quantity,unit_price) values(?,?,?,?)";
			psItem=con.prepareStatement(Order_ItemQuery);
			for(OrderItem item:order.getOrders()) {
				psItem.setInt(1, orderId);
				psItem.setInt(2,item.getBook_id());
				psItem.setInt(3, item.getQuantity());
				psItem.setDouble(4, item.getUnit_price());
				psItem.addBatch();
			}
			psItem.executeBatch();
			String StockQuery="update books set stock=stock-? where book_id=?";
	        psStock=con.prepareStatement(StockQuery);
			for(OrderItem item:order.getOrders()) {
				// stock updation
		        psStock.setInt(1,item.getQuantity());
		        psStock.setInt(2,item.getBook_id());
		        psStock.addBatch();
			}
			psStock.executeBatch();
			con.commit();
			return orderId;
		} catch(Exception e){
			try {con.rollback();}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try  {con.setAutoCommit(true);}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return orderId;
	}

	@Override
	public List<Order> getOrdersByCustomer(int customer_id) {
		List<Order> orders=new ArrayList<Order>();
		String query="select o.order_id,o.order_date,o.total_amount,b.book_id,b.title, oi.quantity,oi.unit_price " +
		"from orders o " +
		"join order_items oi on o.order_id=oi.order_id " +
		"join books b on b.book_id=oi.book_id " +
		"where o.customer_id=? " +
		"order by o.order_id";
		
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1, customer_id);
			ResultSet rs=ps.executeQuery();
			
			Map<Integer,Order> orderMap=new HashMap<>();
			while(rs.next()) {
				int orderId=rs.getInt("order_id");
				Order order=orderMap.get(orderId);
				if(order==null) {
					order=new Order();
					order.setCustomer_id(customer_id);
					order.setOrder_id(orderId);
	                order.setOrder_date(rs.getDate("order_date"));
	                order.setTotal_amount(rs.getDouble("total_amount"));
					order.setOrders(new ArrayList<>());
					orderMap.put(orderId, order);
				}
				OrderItem item = new OrderItem();
	            item.setBook_id(rs.getInt("book_id"));
	            item.setQuantity(rs.getInt("quantity"));
	            item.setUnit_price(rs.getDouble("unit_price"));
	            item.setOrder_id(orderId);
	            order.getOrders().add(item);
			}
			orders.addAll(orderMap.values());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
}
