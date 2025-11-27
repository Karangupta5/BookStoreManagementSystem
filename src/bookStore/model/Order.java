package bookStore.model;

import java.sql.Date;
import java.util.*;

public class Order {
	private int order_id,customer_id;
	private Date order_date;
	private double total_amount;
	
	private List<OrderItem> orders=new ArrayList<OrderItem>(); 
	
	public Order() {
		
	}
	
	public Order(int customerId) {
        this.customer_id = customerId;
    }

    public Order(int orderId, int customerId, Date orderDate, double totalAmount) {
        this.order_id = orderId;
        this.customer_id = customerId;
        this.order_date = orderDate;
        this.total_amount = totalAmount;
    }
	
	public Date getOrder_date() {
		return order_date;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public int getCustomer_id() {
		return customer_id;
	}
	
	public void setOrders(List<OrderItem> orders) {
		this.orders = orders;
	}

	public double getTotalAmount() {
		return total_amount;
	}
	
	 public void addItem(OrderItem item) {
	        orders.add(item);
	        total_amount += item.getTotalPrice();
	    }

	    public List<OrderItem> getOrders() {
	        return orders;
	    }
	    @Override
	    public String toString() {
	        return "Order{" +
	                "orderId=" + order_id +
	                ", customerId=" + customer_id +
	                ", orderDate='" + order_date + '\'' +
	                ", totalAmount=" + total_amount +
	                ", items=" + orders +
	                '}';
	    }
	
	
}
