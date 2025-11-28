package bookStore.app;

import bookStore.model.*;
import bookStore.service.*;
import bookStore.DAO.*;
import java.sql.SQLException;
import java.util.*;
import java.util.Scanner;
import bookStore.exception.*;

public class BookStoreApp {
	private static Scanner sc = new Scanner(System.in);
    private static BookService bookService = new BookServiceImpl();
    private static CustomerService customerService = new CustomerServiceImpl();
    private static CustomerDAO customerDao = new CustomerDAOImpl();
    private static OrderService orderService = new OrderServiceImpl();

	public static void main(String[] args) throws SQLException, InvalidInputException, CustomerNotFoundException, InsufficientStockException {

		        System.out.println("===========================================");
		        System.out.println(" 📚 WELCOME TO THE BOOKSTORE MANAGEMENT ");
		        System.out.println("===========================================");

		        while (true) {
		            showMenu();
		            int choice = readInt(sc,"Enter your choice: ");

		            switch (choice) {
		                case 1 -> manageBooks();
		                case 2 -> manageCustomers();
		                case 3 -> placeOrder();
		                case 4 -> viewCustomerOrders();
		                case 5 -> {
		                    System.out.println("\nThank you for using Bookstore App! 👋");
		                    System.exit(0);
		                }
		                default -> System.out.println("❌ Invalid choice! Try again.");
		            }

		            pressEnterToContinue();
		        }
		    }

		    private static void showMenu() {
		        System.out.println("\n============================");
		        System.out.println(" MAIN MENU");
		        System.out.println("============================");
		        System.out.println("1️⃣  Manage Books");
		        System.out.println("2️⃣  Manage Customers");
		        System.out.println("3️⃣  Place Order");
		        System.out.println("4️⃣  View Customer Order History");
		        System.out.println("5️⃣  Exit");
		        System.out.println("============================");
		    }

		    // ------- Book Actions -------
		    private static void manageBooks() {
		    	boolean stay=true;
		    	while(stay) {
		        System.out.println("\n--- Manage Books ---");
		        System.out.println("1. Add Book");
		        System.out.println("2. View All Books");
		        System.out.println("3. Update Stock");
		        System.out.println("4. Delete Book");
		        System.out.println("5. Back to Main");
		        int ch = readInt(sc,"Enter: ");

		        switch (ch) {
		            case 1:{
		            	try {
							bookService.addBook(new Book(readInt(sc,"Enter Book Id:"),readString(sc,"Enter Title of book"),readString(sc,"Enter Author's name"),readDouble(sc,"Enter price"),readInt(sc,"Enter Stock")));
							break;
						} catch (InvalidInputException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            case 2 : System.out.println(bookService.viewAllBooks());
		                     break;
		            case 3 : try {
						bookService.updateStock(readInt(sc,"Enter BookId"),readInt(sc,"Enter updated Stock"));
						break;
					} catch (BookNotFoundException | InvalidInputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            case 4 : try {
						bookService.deleteBook(readInt(sc,"Enter BookId"));
						break;
					} catch (BookNotFoundException e) {
						e.printStackTrace();
					}
		            case 5:  stay=false;
		        }
		    	}
		    }

		    // ------- Customer Actions -------
		    private static void manageCustomers() throws InvalidInputException, CustomerNotFoundException {
		    	boolean stay=true;
		    	while(stay) {
		        System.out.println("\n--- Manage Customers ---");
		        System.out.println("1. Add Customer");
		        System.out.println("2. View All Customers");
		        System.out.println("3. View Customer By Id");
		        System.out.println("4. Delete Customer");
		        System.out.println("5. Back to Main");

		        int ch = readInt(sc,"Enter: ");
		        switch (ch) {
		            case 1 : {
		            	int customerId=readInt(sc,"Enter CustomerID ");
		                try {
		            	if(customerDao.exists(customerId)) throw new InvalidInputException("Customer already exist");
		                } catch( InvalidInputException | SQLException e) {
		                	e.printStackTrace();
		                	break;
		                }
		                String name=readString(sc,"Enter Customer's Name ");
		                try {
		                if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Customer name cannot be empty");
		                } catch( InvalidInputException e) {
		                	e.printStackTrace();
		                	break;
		                }
		                String email=readString(sc,"Enter Customer's Email ");
		                try {
		        	    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) throw new InvalidInputException("Invalid email format");
		                } catch( InvalidInputException e) {
		                	e.printStackTrace();
		                	break;
		                }
		                String phone=readString(sc,"Enter Customer's Phone number ");
		                try {
		        	    if(phone.length() != 10)  throw new InvalidInputException("Phone number must be 10 digits ");
		                } catch( InvalidInputException e) {
		                	e.printStackTrace();
		                	break;
		                }
		            	
		            	customerService.addCustomer(new Customer(customerId,name,email,phone));
		            }
		                      
		            case 2 : System.out.println(customerService.getAllCustomers());
		                      break;
		            case 3 : customerService.getCustomerById(readInt(sc,"Enter Customer ID"));
		                      break;
		            case 4 : customerService.deleteCustomer(readInt(sc,"Enter Customer ID"));
		                      break;
		            case 5:  stay=false;
		        }
		    	}
		    }

		    // ------- Order Actions -------
		    private static void placeOrder() throws SQLException, InsufficientStockException, InvalidInputException {
		    	int CustomerId=readInt(sc,"Enter CustomerID: ");
		    	Order order=new Order(CustomerId);
		    	boolean check=true;
		    	double total=0;
		    	List<OrderItem> items=new ArrayList<>();
		    	while(check) {
		    	Book book=null;
		    	int BookId=readInt(sc,"Enter BookId: ");
		    	try {
					book=bookService.searchById(BookId);
				} catch (BookNotFoundException e) {
					e.printStackTrace();
				}
		    	int qty=readInt(sc,"Enter quantity you want to buy: ");
		    	double price=book.getPrice()*qty;
		    	OrderItem item=new OrderItem();
		    	item.setBook_id(BookId);
		    	item.setQuantity(qty);
		    	item.setUnit_price(book.getPrice());
		    	total+=price;
		    	items.add(item);
		    	int ch=readInt(sc,"Enter 1. To Add more Items or 2. To Place Order");
		    	check=(ch==1)? true:false;
		    }
		    	order.setOrders(items);
		    	order.setTotal_amount(total);
		    	orderService.placeOrder(order);
		    }

		    private static void viewCustomerOrders() throws SQLException {
		        int id = readInt(sc,"Enter customer ID: ");
		        System.out.println(orderService.getOrdersByCustomer(id));
		    }

		    // Utility methods
		    private static String readString(Scanner sc, String prompt) {
			    String input;
			    while (true) {
			        System.out.print(prompt);
			        input = sc.nextLine().trim();

			        if (!input.isEmpty()) {
			            return input;
			        }

			        System.out.println("Input cannot be empty. Please try again.");
			    }
			}
			
			private static int readInt(Scanner sc, String prompt) {
			    while (true) {
			        System.out.print(prompt);
			        String input = sc.nextLine().trim();

			        try {
			            return Integer.parseInt(input);
			        } catch (NumberFormatException e) {
			            System.out.println("Invalid number! Please enter a valid integer.");
			        }
			    }
			}
			
			private static double readDouble(Scanner sc, String prompt) {
			    while (true) {
			        System.out.print(prompt);
			        String input = sc.nextLine().trim();

			        try {
			            return Double.parseDouble(input);
			        } catch (NumberFormatException e) {
			            System.out.println("Invalid number! Please enter a valid double.");
			        }
			    }
			}

		    private static void pressEnterToContinue() {
		        System.out.print("\nPress Enter to continue...");
		        sc.nextLine();
		    }

	}
