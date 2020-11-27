<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.BoardDAO" %>
<%@ page import="board.BoardDTO" %>

<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
	<%@include file="/static/header.jsp"%><!-- header -->

	<%
		if(userID == null) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.jsp");
			return;
		}

		String boardID = null;
		if(request.getParameter("boardID") != null) {
			boardID = (String) request.getParameter("boardID");
		}

		if(boardID == null || boardID.equals("")) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","게시물을 선택해주세요.");
			response.sendRedirect("index.jsp");
			return;
		}

		BoardDAO boardDAO = new BoardDAO();
		BoardDTO board = boardDAO.getBoard(boardID);
		if(board.getBoardAvailable() == 0) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","삭제된 게시물입니다.");
			response.sendRedirect("boardView.jsp");
			return;
		}

		boardDAO.hit(boardID);
	%>
</head>
<body>
	<%@include file="/static/body-h.jsp"%><!-- body-h -->

	<div class="container">
		<table class="table table-bordered table-hover"
			style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="4"><h4>게시물 보기</h4></th>
				</tr>
				<tr>
					<td style="background-color: #fafafa; color: #000000; width: 80px;"><h5>제목</h5></td>
					<td colspan="3"><h5><%= board.getBoardTitle() %></h5></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; color: #000000; width: 80px;"><h5>작성자</h5></td>
					<td colspan="3"><h5><%= board.getUserID() %></h5></td>
				</tr>
				
			    <tr>
					<td style="background-color: #fafafa; color: #000000; width: 80px;"><h5>작성 날짜</h5></td>
					<td><h5><%= board.getBoardDate() %></h5></td>
					<td style="background-color: #fafafa; color: #000000; width: 80px;"><h5>조회수</h5></td>
					<td><h5><%= board.getBoardHit() + 1 %></h5></td>
				</tr>
				
				<tr>
					<td style="vertical-align: middle; min-height: 150px; background-color: #fafafa; color: #000000; width: 80px;"><h5>글 내용</h5></td>
					<td colspan="3" style="text-align: left;"><h5><%= board.getBoardContent() %></h5></td>
				</tr>
				
				<tr>
					<td style="background-color: #fafafa; color: #000000; width: 80px;"><h5>첨부파일</h5></td>
					<td colspan="3"><h5> <a href="boardDownload.jsp?boardID=<%= board.getBoardID() %>"> <%= board.getBoardFile() %></a></h5></td>
				</tr>

				
			</thead>
			<tbody>
				<tr>
					
					<td colspan="5" style="text-align: right;">
					
					
					<a href="boardView.jsp" class="btn btn-primary">목록</a>
					<a href="boardReply.jsp?boardID=<%= board.getBoardID() %>" class="btn btn-primary">답변</a>
					<%
						if(userID.equals(board.getUserID())) {
					%>
						<a href="boardUpdate.jsp?boardID=<%= board.getBoardID() %>" class="btn btn-primary" type="submit">수정</a>
						<a href="BoardDeleteServlet?boardID=<%= board.getBoardID() %>" class="btn btn-primary" onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</a>
					<%
						}
					%>
					</td>
				
				</tr>
			</tbody>
		</table>
	</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->
	
</body>
</html>