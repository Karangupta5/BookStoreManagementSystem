package bookStore.model;

public class Customer {
	private int customer_id;
	private String name,email;
	private String phone;
	
	public Customer() {
		
	}
	
	public Customer(int customer_id, String name, String email, String phone){
		this.customer_id = customer_id;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_No() {
		return phone;
	}
	public void setPhone_No(String phone_No) {
		this.phone = phone_No;
	}

	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ "]";
	}
	
}
