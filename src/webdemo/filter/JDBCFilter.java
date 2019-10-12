package webdemo.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import webdemo.conn.ConnectionUtils;
import webdemo.utils.MyUtils;

@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter{

	public JDBCFilter() {}
	
	@Override
	public void init(FilterConfig fconfig){
	}
	
	@Override 
	public void destroy() {
	}
	
	private boolean needJDBC(HttpServletRequest req) {
		
		System.out.println("JDBC Filter");
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String urlPattern = servletPath;
		
		if(pathInfo != null) {
			urlPattern = servletPath + "/*"; 
		}
		
		Map<String, ? extends ServletRegistration> servletRegistration = req.getServletContext().getServletRegistrations();
		
		Collection<? extends ServletRegistration> values = servletRegistration.values();
		
		for(ServletRegistration sr : values) {
			Collection<String> mappings = sr.getMappings();
			if(mappings.contains(urlPattern)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		if(this.needJDBC(request)) {
			System.out.println("Open connection for: "+ request.getServletPath());
			
			Connection conn = null;
			
			try {
				conn = ConnectionUtils.getConnection();
				conn.setAutoCommit(false);
				MyUtils.storeConnection(req, conn);
				chain.doFilter(req, res);
				conn.commit();
			}catch (Exception e) {
				e.printStackTrace();
				ConnectionUtils.rollbackQuietly(conn);
				//throw new ServletException();
			} finally {
				ConnectionUtils.closeQuietly(conn);
			}
		} else {
			chain.doFilter(req, res);
		}
	}
}
