package webdemo.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtils {
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		return MySQLConnection.getMySQLConnection();
	}
	
	public static void closeQuietly (Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void rollbackQuietly (Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			
		}
	}
	
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		// TODO Auto-generated method stub
//		
//		System.out.println("Get connection ... ");
//		
//		Connection conn = ConnectionUtils.getConnection();
//		
//		System.out.println("Get connection " + conn);
//	    System.out.println("Done!");
//		
//	    Statement statement = conn.createStatement();
//	    
//	    // SELECT * 
//	    /*
//	    String sql = "Select * from Employee";
//	    
//	    ResultSet rs = statement.executeQuery(sql);
//	    
//	    while(rs.next()) {
//	    	
//	    	String empName = rs.getString(2);
//	    	int empID = rs.getInt(1);
//	    	String empNo = rs.getString("emp_no");
//	    	System.out.println("-----------------");
//	    	System.out.println("ID = " + empID + " NAME : " + empName + " NO : " + empNo);
//	    	
//	    }
//	    
//	    conn.close();
//	    */
//	    
//	    // INSERT
//	    
//	    /*String sql = "Insert into Salary_Grade (Grade, High_Salary, Low_Salary) values (2, 20000, 10000)";
//	    
//	    int count = statement.executeUpdate(sql);
//	    
//	    System.out.println(count);
//	   
//	    conn.close();*/
//	    
//	    String sql = "select * from product ";
//	    
//	    PreparedStatement pstm = conn.prepareStatement(sql);
//	   
//	    
//	    ResultSet rs = pstm.executeQuery();
//	    
//	    while(rs.next()) {
//	    	System.out.println("Name : " + rs.getString("name"));
//	    }
//	   
//	    
//	 
//	}

}


