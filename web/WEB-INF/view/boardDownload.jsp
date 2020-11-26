<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.BoardDAO" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"> <!-- 부트스트랩을 넣었을때 반응형 웹이 잘 출력될수 있도록 뷰포트를 넣어준다.  -->
	<link rel="stylesheet" href="css/bootstrap.css"> <!--css 파일 불러오기  -->
	<link rel="stylesheet" href="css/custom.css"> <!--css 파일 불러오기  -->
	
	<title>JSP Ajax 실시간 회원제 채팅 서비스</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> <!--ajax 를 위해서 공식사이트에서 제공하는 제이쿼리를 링크로 가져온다.  -->
	<script src="js/bootstrap.js"></script> <!--마찬가지로 우리가 받았던 부트스트랩 안의 js 페이지도 가져온다. -->
	
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		String boardID = request.getParameter("boardID");
		if(boardID ==null || boardID.equals("")) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "upload";
		String fileName = "";
		String realFile = "";
		BoardDAO boardDAO = new BoardDAO();
		fileName = boardDAO.getFile(boardID);
		realFile = boardDAO.getRealFile(boardID);
		if(fileName.equals("") || realFile.equals("")) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}

		//기본적인 소켓프로그래밍 방식
		InputStream in = null;
		OutputStream os = null;
		File file = null;
		boolean skip = false;
		String client = "";
		try {
			try {
				file = new File(savePath, realFile);
				in = new FileInputStream(file);
			} catch (Exception e) {
				skip = true;
			}
			client = request.getHeader("User-Agent");
			response.reset();
			response.setContentType("application/octet-stream");
			if(!skip) {
				if(client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("KSC5601"), "ISO8859_1"));
				} else {
					fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
					
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int)file.length()];
				int leng = 0;
				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html; charset=UTF-8");
				out.println("<script> alert('파일을 찾을 수 없습니다.');history.back();</script>");
			}
			in.close();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	%>
</body>
</html>