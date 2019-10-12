package webdemo.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webdemo.beans.Product;
import webdemo.utils.DBUtils;
import webdemo.utils.MyUtils;

@WebServlet(urlPatterns = {"/createProduct"})
public class CreateProductServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public CreateProductServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		dispatcher.forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Connection conn  = MyUtils.getStoredConnection(req);
		String errorString = null;
		
		String code = (String) req.getParameter("productCode");
		String name = (String) req.getParameter("productName");
		String priceString =  req.getParameter("productPrice");
		float price = 0;
		try {
			price = Float.parseFloat(priceString);
		} catch(Exception e) {
			
		}
		
		Product product = new Product(code, name, price);
		
		if(code == null || name == null || priceString == null || code.length() == 0 || name.length() == 0) {
			errorString = "Error input!!!";
		}
		
		if(errorString == null) {
			
			try {
				DBUtils.insertProduct(conn, product);
			} catch (Exception e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		req.setAttribute("errorString", errorString);
		req.setAttribute("product", product);
		
		if(errorString != null) {
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
			dispatcher.forward(req, res);
		} else {
			res.sendRedirect(req.getContextPath()+ "/productList");
		}
	}
}
