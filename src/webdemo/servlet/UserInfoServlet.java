package webdemo.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webdemo.beans.UserAccount;
import webdemo.utils.MyUtils;

@WebServlet(urlPatterns = {"/userInfo"})
public class UserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	 
    public UserInfoServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
    	
    	HttpSession session = req.getSession();
    	
    	UserAccount loginedUser = MyUtils.getLoginedUser(session); 
    	
    	if(loginedUser == null) {
    		res.sendRedirect(req.getContextPath() + "/login");
    		return;
    	}
    	
    	req.setAttribute("user", loginedUser);
    	
    	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
    	dispatcher.forward(req, res);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
    	this.doGet(req, res);
    }
}
