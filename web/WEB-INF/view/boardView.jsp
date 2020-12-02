<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.dao.BoardDAOImpl" %>
<%@ page import="board.dto.BoardDTO" %>
<%@ page import="java.util.ArrayList" %>

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



		String pageNumber = "1";
		if(request.getParameter("pageNumber") != null) {
			pageNumber = request.getParameter("pageNumber");
		}
		try {
			Integer.parseInt(pageNumber);
		} catch (Exception e){
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","페이지 번호가 잘못되었습니다.");
			response.sendRedirect("boardView.jsp");
			return;

		}
		ArrayList<BoardDTO> boardList = new BoardDAOImpl().getList(pageNumber);
	%>
</head>
<body>
	<%@include file="/static/body-h.jsp"%><!-- body-h -->


	<div class="container">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="5"><h4>자유 게시판</h4></th>
					</tr>
					<tr>
						<th style="background-color: #fafafa; color: #000000; width: 70px;"><h5>번호</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>제목</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>작성자</h5></th>
						<th style="background-color: #fafafa; color: #000000; width: 100px;"><h5>작성 날짜</h5></th>
						<th style="background-color: #fafafa; color: #000000; width: 70px;"><h5>조회수</h5></th>
					</tr>
				</thead>
				<tbody>
				<%
					for(int i=0; i< boardList.size(); i++) {
						BoardDTO board = boardList.get(i);
						board.getUserID();

				%>
					<tr>
						<td><%= board.getBoardID() %></td>
						<td style="text-align: left;"><a href="boardShow.jsp?boardID=<%= board.getBoardID()%>">

						<%
							for (int j=0; j < board.getBoardLevel(); j++) {
						%>
							<sapn class="glyphicon glyphicon-arrow-right" aria-hidden="true"></sapn>
						<%
							}
						%>

						<%
							if(board.getBoardAvailable() == 0) {
						%>
							(삭제된 게시물입니다.)
						<%
							} else {
						%>
							<%= board.getBoardTitle() %>
						<%
							}
						%>

						</a></td>
						<td><%= board.getUserID()%></td>
						<td><%= board.getBoardDate()%></td>
						<td><%= board.getBoardHit()%></td>
					</tr>
				<%
					}
				%>
					<tr>
						<td colspan="5"><a href="boardWrite.jsp" class="btn btn-primary pull-right" type="submit">글쓰기</a>
						<ul class="pagination" style="margin: 0 auto;">

				<%
					int startPage = (Integer.parseInt(pageNumber) / 10) * 10 + 1 ;
					if(Integer.parseInt(pageNumber) % 10 == 0 ) startPage -= 10;
					int targetPage = new BoardDAOImpl().targetPage(pageNumber);

					if(startPage != 1) {
				%>

				<li><a href="boardView.jsp?pageNumber=<%= startPage - 1 %>" ><span class="glyphicon glyphicon-chevron-left"></span></a></li>

				<% } else {
				%>

				<li><span class="glyphicon glyphicon-chevron-left" style="color: gray;"></span></li>

				<%
					}
					for(int i = startPage; i < Integer.parseInt(pageNumber); i++) {
				%>
				<li><a href="boardView.jsp?pageNumber=<%= i %>" ><%= i %></a></li>
				<%
					}
				%>
					<li class="active"><a href="boardView.jsp?pageNumber=<%= pageNumber %>" ><%= pageNumber %></a></li>
				<%
					for(int i = Integer.parseInt(pageNumber) + 1; i <= targetPage + Integer.parseInt(pageNumber); i++) {
						if(i < startPage + 10) {
				%>
					<li><a href="boardView.jsp?pageNumber=<%= i %>" ><%= i %></a></li>
				<%
						}
					}
				if(targetPage + Integer.parseInt(pageNumber) > startPage + 9) {
				%>
				<li><a href="boardView.jsp?pageNumber=<%= startPage + 10 %>"><span class="glyphicon glyphicon-chevron-right"></span></a></li>
				<%
					} else {
				%>
					<li><span class="glyphicon glyphicon-chevron-right" style="color: gray;"></span></li>
				<%
					}
				%>
					</ul>

						</td>
					</tr>
				</tbody>
			</table>
		</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->

</body>
</html>