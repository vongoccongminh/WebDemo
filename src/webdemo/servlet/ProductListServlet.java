package webdemo.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webdemo.beans.Product;
import webdemo.utils.DBUtils;
import webdemo.utils.MyUtils;

@WebServlet(urlPatterns = {"/productList"})
public class ProductListServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	 
    public ProductListServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
 
    	Connection conn = MyUtils.getStoredConnection(req);
    	String errorString = null;
    	List<Product> list = null;
    	
    	try {
    		list = DBUtils.queryProduct(conn);
    	} catch (Exception e) {
    		e.printStackTrace();
    		errorString = e.getMessage();
    	}
    	
    	req.setAttribute("productList", list);
    	req.setAttribute("errorString", errorString);
    	
    	RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/productListView.jsp");
    	dispatcher.forward(req, res);	
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	this.doGet(req, res);
    }
}
