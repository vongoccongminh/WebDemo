package webdemo.filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webdemo.beans.UserAccount;
import webdemo.utils.DBUtils;
import webdemo.utils.MyUtils;

@WebFilter(filterName = "cookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter{

	public CookieFilter() {
		
	}
	
	@Override
	public void init(FilterConfig fconfig) {
		
	}
	
	@Override 
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		
		UserAccount userInSession = MyUtils.getLoginedUser(session);
		
		if(userInSession != null) {
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
			chain.doFilter(req, res);
			return;
		}
		
		Connection conn = MyUtils.getStoredConnection(req);
		
		String checked = (String) session.getAttribute("COOKIE_CHECKED");
		
		if(checked == null && conn != null) {
			String userName = MyUtils.getUserNameInCookie(request);
			try {
				UserAccount user = DBUtils.findUser(conn, userName);
				MyUtils.storeLoginedUser(session, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
		}
		chain.doFilter(req, res);
	}
}
