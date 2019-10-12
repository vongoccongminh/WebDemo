package webdemo.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webdemo.beans.Product;
import webdemo.utils.DBUtils;
import webdemo.utils.MyUtils;

@WebServlet(urlPatterns = {"/editProduct"})
public class EditProductServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public EditProductServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		Connection conn = MyUtils.getStoredConnection(req);
		Product product = null;
		String errorString = null;
		
		String code = req.getParameter("code");
		
		try {
			product = DBUtils.findProduct(conn, code);
		} catch (Exception e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		if(errorString != null && product == null) {
			res.sendRedirect(req.getServletPath() + "/productList");
			return;
		}
		
		req.setAttribute("errorString", errorString);
		req.setAttribute("product", product);
		
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(req);
		
		String code = req.getParameter("code");
		String name = req.getParameter("name");
		String priceString = (String) req.getParameter("price");
		float price = 0;
		
		try {
			price = Float.parseFloat(priceString);
		} catch (Exception e) {
			
		}
		
		Product product = new Product(code, name, price);
		String errorString = null;
		try {
			DBUtils.updateProduct(conn, product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		req.setAttribute("errorString", errorString);
		req.setAttribute("product", product);
		
		if(errorString != null) {
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
			dispatcher.forward(req, res);
		} else {
			res.sendRedirect(req.getContextPath() + "/productList");
		}
	}
}
