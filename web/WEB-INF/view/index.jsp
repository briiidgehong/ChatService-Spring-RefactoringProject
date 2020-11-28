<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>


<head>
	<%
		String userID = null; //세션관리
		if (session.getAttribute("userID") != null) { // 세션값이 존재한다면
			response.sendRedirect("/box");
		}else{
			response.sendRedirect("/login");
		}
	%>
</head>
<body>
</body>
</html>