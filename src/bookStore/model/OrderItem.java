package bookStore.model;

public class OrderItem {
	private int order_id,book_id,quantity;
	private double unit_price;
	
	public OrderItem() {
		
	}
	public OrderItem(int order_id,int book_id,int quantity,double unit_price) {
		this.order_id=order_id;
		this.book_id=book_id;
		this.quantity=quantity;
		this.unit_price=unit_price;
	}
	
	public int getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public int getBook_id() {
		return book_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public double getTotalPrice() {
		return unit_price*quantity;
	}
	@Override
	public String toString() {
		return "OrderItem [ order_id=" + order_id + ", book_id=" + book_id + ", quantity=" + quantity + ", unit_price="
				+ unit_price + "]";
	}
	
	
	
	
}
