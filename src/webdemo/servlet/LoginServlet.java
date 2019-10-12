package webdemo.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webdemo.beans.UserAccount;
import webdemo.utils.DBUtils;
import webdemo.utils.MyUtils;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		
		dispatcher.forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String rememberMe = req.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMe);
		
		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;
		
		if(userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			hasError = true;
			errorString = "Required username and password!";
		} else {
		
			Connection conn = MyUtils.getStoredConnection(req);
		
			try {
				user = DBUtils.findUser(conn, userName);
				if (user == null) {
					hasError = true;
					errorString = "User Name or password invalid";
				}
			} catch (Exception e){
					e.printStackTrace();
	                hasError = true;
	                errorString = e.getMessage();
			}
		}
		if (hasError) {
			user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			
			req.setAttribute("errorString", errorString);
			req.setAttribute("user", user);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			dispatcher.forward(req, res);
		} else {
			HttpSession session = req.getSession();
			MyUtils.storeLoginedUser(session, user);
			
			if(remember) {
				MyUtils.storeUserCookie(res, user);
			} else {
				MyUtils.deleteUserCookie(res);
			}
			
			res.sendRedirect(req.getContextPath() + "/userInfo");
		}
	}
}

