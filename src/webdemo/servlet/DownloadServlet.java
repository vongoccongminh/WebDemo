package webdemo.servlet;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.fxml.ParseTraceElement;

import webdemo.beans.Attachment;
import webdemo.conn.ConnectionUtils;

@WebServlet(urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {

	public DownloadServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		
		Connection conn = null;
		
		try {
			conn = ConnectionUtils.getConnection();
			Long id = null;
			
			id = Long.parseLong(req.getParameter("id"));
			
			Attachment attachment = getAttachment(conn, id);
			
			if(attachment == null) {
				res.getWriter().write("No data found");
				return;
			}
			
			String fileName = attachment.getFileName();
			System.out.println("File name : " + fileName);
			String contentType = this.getServletContext().getMimeType(fileName);
			System.out.println("Content Type: " + contentType);
			
			res.setHeader("Content-Type", contentType);
			res.setHeader("Content-Length", String.valueOf(attachment.getFileData().length()));
			res.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getFileName() + "\"");
			
			Blob fileData = attachment.getFileData();
	           InputStream is = fileData.getBinaryStream();
	 
	           byte[] bytes = new byte[1024];
	           int bytesRead;
	 
	           while ((bytesRead = is.read(bytes)) != -1) {
	               // Ghi dữ liệu ảnh vào Response.
	               res.getOutputStream().write(bytes, 0, bytesRead);
	           }
	           is.close();
			
		}catch(Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				this.closeQuietly(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Attachment getAttachment(Connection conn, Long id) throws SQLException {
		
		String sql = "select * from attachment where id = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setLong(1, id);
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			String fileName = rs.getString(2);
			Blob fileData = rs.getBlob(3);
			String description = rs.getString(4);
			
			return new Attachment(id, fileName, fileData, description);
		}
		
		return null;
	}
	
	private void closeQuietly(Connection conn) throws SQLException {
		if(conn != null) {
			conn.close();
		}
	}
}
