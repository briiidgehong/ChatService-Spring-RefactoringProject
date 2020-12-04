<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="user.dao.UserDAOImpl"%>
<%@ page import="org.springframework.web.context.ContextLoader" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="user.service.UserServiceImpl" %>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
	<%@include file="/static/header.jsp"%><!-- header -->
	<%
		String toID = null;
		if (request.getParameter("toID") != null){
			toID=(String) request.getParameter("toID");
		}
		/*
		if(userID == null) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		if(toID == null) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","대화 상대가 지정되지 않았습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		if(userID.equals(URLDecoder.decode(toID, "UTF-8"))) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","자기 자신과는 채팅할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		*/
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		UserServiceImpl us =(UserServiceImpl)context.getBean(UserServiceImpl.class);

		String fromProfile = us.getProfile(userID);
		String toProfile = us.getProfile(toID);
	%>
	<script type="text/javascript">
	
	//실제로 채팅 메시지를 다른 사람에게 보내는 함수를 ajax 비동기 통신으로 구현
		function autoClosingAlert(selector, delay) {
			var alert = $(selector).alert();
			alert.show();
			window.setTimeout(function() { alert.hide()}, delay);
		}
	
		function submitFunction() {
			var fromID = '<%=userID%>';
			var toID = '<%=toID%>';
			var chatContent = $('#chatContent').val();
			$.ajax({
				type: "POST",
				url: "/chat/submit",
				data: {
					fromID: encodeURIComponent(fromID),
					toID: encodeURIComponent(toID),
					chatContent: encodeURIComponent(chatContent)},
				success: function(result){
					if(result ==1) {
						autoClosingAlert('#successMessage', 2000);
					} else if (result ==0){
						autoClosingAlert('#dangerMessage', 2000);
					} else {
						autoClosingAlert('#warningMessage', 2000);
					}
				}
			});
			$('#chatContent').val(' '); //값 비우기
		}
		
		var lastID = 0;
		
		function chatListFunction(Type) {
			var fromID = '<%=userID %>';
			var toID = '<%=toID %>';
			$.ajax({
				type : "POST",
				url : "/chat/list",
				data : {
					fromID : encodeURIComponent(fromID),
					toID : encodeURIComponent(toID),
					listType : Type
				},
				success : function(data) {
					if (data == "")
						return;
					var parsed = JSON.parse(data);
					var result = parsed.result;
					for (var i = 0; i < result.length; i++) {
						if(result[i][0].value == fromID){
							result[i][0].value ='나';
						}
						addChat(result[i][0].value, result[i][2].value, result[i][3].value);
					}
					lastID = Number(parsed.last);
				}
			});
		}

		function addChat(chatName, chatContent, chatTime) {
			if(chatName == '나') {
			$('#chatList').append(
							'<div class="row">'
									+ '<div class="col-lg-12">'
									+ '<div class="media">'
									+ '<a class="pull-left" href="#">'
									+ '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="/upload/<%=fromProfile%>" alt="">'
									+ '</a>' + '<div class="media-body">'
									+ '<h4 class="media-heading">' + chatName
									+ '<span class="small pull-right">'
									+ chatTime + '</span>' + '</h4>' + '<p>'
									+ chatContent + '</p>' + '</div>'
									+ '</div>' + '</div>' + '</div>' + '<hr>');
			} else {
				$('#chatList').append(
						'<div class="row">'
								+ '<div class="col-lg-12">'
								+ '<div class="media">'
								+ '<a class="pull-left" href="#">'
								+ '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="/upload/<%=toProfile%>" alt="">'
								+ '</a>' + '<div class="media-body">'
								+ '<h4 class="media-heading">' + chatName
								+ '<span class="small pull-right">'
								+ chatTime + '</span>' + '</h4>' + '<p>'
								+ chatContent + '</p>' + '</div>'
								+ '</div>' + '</div>' + '</div>' + '<hr>');				
				
			}
			$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
		
		}
		
		function getInfiniteChat() {
			setInterval(function() {
				chatListFunction(lastID);
			}, 1000);
		}
	</script>
</head>
<body>
	<%@include file="/static/body-h.jsp"%><!-- body-h -->

	<div class="container bootstrap snippet">
			<div class="row">
				<div class="portlet portlet-default">
					<div class="portlet-heading">
						<div class="portlet-title">
							<h4><i class="fa fa-circle text-green">실시간 채팅창</i></h4>
						</div>
						<div class="clearfix"></div>
					</div>
					<div id="chat" class="panel-collapse collapse in">
						<div id="chatList" class="portlet-body chat-widget"
							style="overflow-y: auto; width: auto; height: 500px;"></div>

						<div class="portlet-footer">
							<div class="row" style="height: 90px;">
								<div class="form-group col-xs-10">
									<textarea style="height: 80px;" id="chatContent"
										class="form-control" placeholder="메시지를 입력하세요." maxlength="100"></textarea>
								</div>
								<div class="form-group col-xs-2">
									<button type="button" class="btn btn-default pull-right"
										onclick="submitFunction();">전송</button>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	
<!-- 메시지 전송시 alert -->
	<div class="alert alert-success" id="successMessage" style="display: none;">
		<strong>메시지 전송에 성공했습니다.</strong>
	</div>
	<div class="alert alert-danger" id="dangerMessage" style="display: none;">
		<strong>이름과 내용을 모두 입력해주세요.</strong>
	</div>
	<div class="alert alert-warning" id="warningMessage" style="display: none;">
		<strong>데이터베이스 오류가 발생했습니다.</strong>
	</div>


	<%@include file="/static/body-f.jsp"%><!-- body-h -->

	<script type="text/javascript">
		$(document).ready(function() {
			chatListFunction('0');
			getInfiniteChat();
		})
	</script>
</body>
</html>