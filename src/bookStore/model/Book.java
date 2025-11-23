package bookStore.model;

public class Book {
	private int book_id;
	private String title,author;
	private Double price;
	private int stock;
	
	public Book() {
		
	}
	
	public Book(int bookId, String title, String author, Double price, int stock) {
		this.book_id = bookId;
		this.title = title;
		this.author = author;
		this.price = price;
		this.stock = stock;
	}

	public int getBookId() {
		return book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Book [bookId=" + book_id + ", title=" + title + ", author=" + author + ", price=" + price + ", stock="
				+ stock + "]";
	}
	
	
	
}
