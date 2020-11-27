<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
</head>
<body>
	<%
		session.invalidate();
	%>
	<script>
		location.href = 'index.jsp';
	</script>
</body>
</html>