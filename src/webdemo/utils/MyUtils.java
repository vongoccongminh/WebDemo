package webdemo.utils;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webdemo.beans.UserAccount;

public class MyUtils {

	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	public static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	
	//luu tru connection vao attribute cua request
	public static void storeConnection(ServletRequest req, Connection conn) {
		req.setAttribute(ATT_NAME_CONNECTION, conn);
	}
	
	//Lay doi tuong Connection da duoc luu tru trong attribute cua request
	public static Connection getStoredConnection(ServletRequest req) {
		Connection conn = (Connection) req.getAttribute(ATT_NAME_CONNECTION);
		return conn;
	}
	
	//Lay thong tin nguoi dung da login vao session
	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
		session.setAttribute("loginedUser", loginedUser);
	}
	
	public static UserAccount getLoginedUser(HttpSession session) {
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		return loginedUser;
	}
	
	//Luu thong tin nguoi dung vao cookie
	public static void storeUserCookie(HttpServletResponse res, UserAccount user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
		cookieUserName.setMaxAge(24 * 60 * 60);
		res.addCookie(cookieUserName);
	}
	
	public static String getUserNameInCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
	public static void deleteUserCookie(HttpServletResponse res) {
		Cookie userCookie = new Cookie(ATT_NAME_USER_NAME, null);
		userCookie.setMaxAge(0);
		res.addCookie(userCookie);
	}
}
