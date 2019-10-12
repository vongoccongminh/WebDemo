<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Info</title>
</head>
<body>
	
	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	
	<h3>User's Information</h3>
	
	<h2>Hello: ${user.userName}</h2>
	User Name : <b>${user.userName}</b>
	
	 <br />
    Gender: ${user.gender } <br />
 
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>