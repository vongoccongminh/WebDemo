package webdemo.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webdemo.beans.Product;
import webdemo.beans.UserAccount;

public class DBUtils {

	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		
		String sql = "select * from UserAccount where userName = ? and password = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			
			String gender = rs.getString("gender");
			UserAccount userAcount = new UserAccount();
			userAcount.setGender(gender);
			userAcount.setPassword(password);
			userAcount.setUserName(userName);
			
			return userAcount;
		}
		
		return null;
	}
	
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		
		String sql = "select * from User_Account where user_name = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			String gender = rs.getString("gender");
			String password = rs.getString("password");
			
			UserAccount user = new UserAccount();
			user.setGender(gender);
			user.setPassword(password);
			user.setUserName(userName);
			
			return user;
		}
		
		return null;
	}
	
	public static List<Product> queryProduct(Connection conn) throws SQLException{
		
		String sql = "select * from product";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()) {
			String code = rs.getString("code");
			String name = rs.getString("name");
			float price = rs.getFloat("price");
			
			Product product = new Product();
			
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			
			list.add(product);
		}
		return list;
	}
	
	public static Product findProduct(Connection conn, String code) throws SQLException {
		
		String sql = "select * from Product where code = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);
		
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			String name = rs.getString("name");
			float price = rs.getFloat("price");
			
			Product product = new Product(code, name, price);
			
			return product;
		}
		return null;
	}
	
	public static void updateProduct(Connection conn, Product product) throws SQLException {
		
		String sql = "update Product set name = ?, price = ? where code = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		
		pstm.executeUpdate();
	}
	
	public static void insertProduct(Connection conn, Product product) throws SQLException {
		
		String sql = "insert into Product(name, price, code) values (?, ?, ?) ";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		
		pstm.executeUpdate();
	}
	
	public static void deleteProduct(Connection conn, String code) throws SQLException {
		
		String sql = "delete from Product where code = ? ";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, code);
		
		pstm.executeUpdate();
	}
}
