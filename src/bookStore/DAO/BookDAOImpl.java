package bookStore.DAO;

import java.sql.*;
import java.util.*;

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

}
