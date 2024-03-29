package webdemo.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/uploadResult"})
public class UploadResult extends HttpServlet {

	public UploadResult() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/uploadResult.jsp");
		
		dispatcher.forward(req, res);
	}
}
