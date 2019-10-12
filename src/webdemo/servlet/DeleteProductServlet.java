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

@WebServlet(urlPatterns = {"/deleteProduct"})
public class DeleteProductServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public DeleteProductServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(req);
		
		String code = req.getParameter("code");
		String errorString = null;
		
		if(code != null) {
			try {
				DBUtils.deleteProduct(conn, code);
			}catch(Exception e) {
				e.getStackTrace();
				errorString = e.getMessage();
			}
		}
		if (errorString != null) {
			req.setAttribute("errorString", errorString);
			
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/productListView.jsp");
			dispatcher.forward(req, res);
		} else {
			res.sendRedirect(req.getContextPath() + "/productList");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
