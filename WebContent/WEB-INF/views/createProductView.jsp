<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Product</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	
	<h2>CREATE PRODUCT</h2>
	
	<p style="color: red;">${errorString}</p>
	
	<form method="post" action="${pageContext.request.contextPath}/createProduct">
		<table>
			<tr>
				<td>Code</td>
				<td><input type="text" name="productCode" value="${product.code}"></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type="text" name="productName" value="${product.name}"></td>
			</tr>
			<tr>
				<td>Price</td>
				<td><input type="text" name="productPrice" value="${product.price}"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="Submit" />
					<a href="productList">Cancel</a>
				</td>
			</tr>
		</table>
	
	</form>
	
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>