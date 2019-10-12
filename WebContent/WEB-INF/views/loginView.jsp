<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	
	<h3>LOGIN</h3>
	<p style="color: red;">${errorString}</p>
	
	<form method="post" action="${pageContext.request.contextPath}/login">
		<table border="0">
			<tr>
				<td>User name</td>
				<td><input type="text" name="userName" value="${user.userName}"/></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" value="${user.password}"/></td>
			</tr>
			<tr>
				<td>Remember me</td>
				<td><input type="checkbox" name="rememberMe" value="Y"/></td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Submit" />
					<a href="${pageContext.request.contextPath}/">Cancel</a>
				</td>
			</tr>
		</table>
	</form>
	
	
      <jsp:include page="_footer.jsp"></jsp:include>
      
</body>
</html>