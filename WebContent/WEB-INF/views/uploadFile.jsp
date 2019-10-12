<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload File</title>
</head>
<body>
	<div style="color: red;">${errorString}</div>
	
	<h2>Upload Files</h2>
	
	<form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
		Select file:
		<br/>
			<input type="file" name="file" />
		<br/>
			<input type="file" name="file" />
		<br/>
		Description:
		<br/>
			<input type="text" name="description" size="100"/>
		<br/>
		<br/>
			<input type="submit" value="Upload" />
	</form>
</body>
</html>