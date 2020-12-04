<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.dao.BoardDAOImpl" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.ContextLoader" %>
<%@ page import="board.service.BoardServiceImpl" %>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
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
		String savePath = root + "/static/upload";
		String fileName = "";
		String realFile = "";

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		BoardServiceImpl bs =(BoardServiceImpl)context.getBean(BoardServiceImpl.class);

		fileName = bs.getFile(boardID);
		realFile = bs.getRealFile(boardID);
		if(fileName.equals("") || realFile.equals("")) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("/index");
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