package bookStore.DAO;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookStore.model.Book;
import bookStore.util.DBUtil;

public class BookDAOImpl implements BookDAO {

	@Override
	public boolean addBook(Book book) {
		String query="insert into books(book_id,title,author,price,stock) values(?,?,?,?,?)";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,book.getBookId());
			ps.setString(2,book.getTitle());
			ps.setString(3,book.getAuthor());
			ps.setDouble(4,book.getPrice());
			ps.setInt(5,book.getStock());
			return ps.executeUpdate()>0;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Book getBookbyId(int bookId) {
		String query="select * from books where book_id= ?";
		Book book=null;
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1, bookId);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				book=new Book(rs.getInt("book_id"),rs.getString("title"),rs.getString("author"),rs.getDouble("price"),rs.getInt("stock"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public boolean updateBook(Book book) {
		String query="update books set price= ?,stock= ? where book_id= ?";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setDouble(1,book.getPrice());
			ps.setInt(2,book.getStock());
			ps.setInt(3,book.getBookId());
			return ps.executeUpdate()>0;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteBook(int bookId) {
		String query="delete from books where book_id= ?";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1, bookId);
			return ps.executeUpdate()>0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> list=new ArrayList<Book>();
		String query="select * from books";
		try(Connection con=DBUtil.getConnection();
			Statement ps=con.createStatement();
			ResultSet rs=ps.executeQuery(query)){
			while(rs.next()) {
				Book book=new Book(rs.getInt("book_id"),rs.getString("title"),rs.getString("author"),rs.getDouble("price"),rs.getInt("stock"));
				list.add(book);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getStock(int bookId) {
		int stk=0;
		String query="select * from books where book_id=?";
		try(Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,bookId);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) stk=rs.getInt("stock");
			System.out.println(stk);
			} catch(SQLException e) {
			e.printStackTrace();
		}
		return stk;
	}

	@Override
	public int getBookStock(int bookId) throws SQLException {
		String sql = "SELECT stock FROM books WHERE book_id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, bookId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("stock");
	        }
	    }
	    return 0; // book not found or zero stock
	}

	public boolean exists(int bookId) throws SQLException {
		final Logger logger = Logger.getLogger(BookDAO.class.getName());
	    String sql = "SELECT count(*) FROM books WHERE book_id=?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, bookId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	logger.info("Checked existence of book ID: " + bookId);
	            return rs.getInt(1) > 0;} // If count > 0 → exists
	        }  catch (SQLException e) {
	            logger.log(Level.SEVERE, "DB error while checking book existence", e);
	        }
	        return false;
	    }

	@Override
	public boolean updateStock(int id, int stock) throws SQLException {
		String query="update books set stock=stock+? where book_id=?";
		final Logger logger = Logger.getLogger(BookDAO.class.getName());
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, stock);
	        ps.setInt(2, id);
	        return ps.executeUpdate()>0;
	        }  catch (SQLException e) {
	            logger.log(Level.SEVERE, "DB error while updating stock", e);
	        }
		return false;
	}
	}
