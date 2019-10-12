package webdemo.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import webdemo.conn.ConnectionUtils;

@WebServlet (urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public UploadServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/uploadFile.jsp");
		dispatcher.forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		Connection conn = null;
		
		try {
			conn = ConnectionUtils.getConnection();
			conn.setAutoCommit(false);
			
			String description = req.getParameter("description");
			
			for (Part part : req.getParts()) {
				
				String fileName = extractNameFile(part);
				
				if(fileName != null && fileName.length() > 0) {
					InputStream is = part.getInputStream();
					this.writeToDB(conn, fileName, is, description);
				}
			}
			conn.commit();
			res.sendRedirect(req.getContextPath() + "/uploadResult");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("errorString", e.getMessage());
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/uploadFile.jsp");
		} finally {
			this.closeQuietly(conn);
		}
	}
	
	private String extractNameFile(Part part) {
		
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		
		for(String s : items) {
			if(s.trim().startsWith("filename")) {
				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() -1);
				clientFileName = clientFileName.replace("\\", "/");
				
				int i = clientFileName.lastIndexOf('/');
				
				return clientFileName.substring(i + 1);
			}
		}
		
		return null;
	}
	
	private Long getMaxAttachmentID(Connection conn) throws SQLException {
		
		String sql = "select max(id) from Attachment";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			Long max = rs.getLong(1);
			return max;
		}
		
		return 0L;
	}
	
	private void writeToDB(Connection conn, String fileName, InputStream is, String description) throws SQLException {
		
		String sql = "insert into Attachment(ID, file_name, file_data, description) values (?,?,?,?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		Long id = getMaxAttachmentID(conn) + 1;
		pstm.setLong(1, id);
		pstm.setString(2, fileName);
		pstm.setBlob(3, is);
		pstm.setString(4, description);
		pstm.executeUpdate();
	}
	
	private void closeQuietly(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		}catch (Exception e) {
			
		}
	}
}
