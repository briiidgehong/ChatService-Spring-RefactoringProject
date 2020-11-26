<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="user.UserDAO"%>  
<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
	<% 
		String userID=null; //세션관리
		if(session.getAttribute("userID") != null) { // 세션값이 존재하지 않는다면
			userID = (String) session.getAttribute("userID"); // 가져와라
		}
		String toID = null;
		if (request.getParameter("toID") != null){
			toID=(String) request.getParameter("toID");
		}
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
		
		String fromProfile=new UserDAO().getProfile(userID);
		String toProfile=new UserDAO().getProfile(toID);
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"> <!-- 부트스트랩을 넣었을때 반응형 웹이 잘 출력될수 있도록 뷰포트를 넣어준다.  -->
	<link rel="stylesheet" href="css/bootstrap.css"> <!--css 파일 불러오기  -->
	<link rel="stylesheet" href="css/custom.css"> <!--css 파일 불러오기  -->
	
	<title>JSP Ajax 실시간 회원제 채팅 서비스</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> <!--ajax 를 위해서 공식사이트에서 제공하는 제이쿼리를 링크로 가져온다.  -->
	<script src="js/bootstrap.js"></script> <!--마찬가지로 우리가 받았던 부트스트랩 안의 js 페이지도 가져온다. -->
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
				url: "./ChatSubmitServlet",
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
				url : "./ChatListServlet",
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
									+ '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="<%=fromProfile%>" alt="">'              
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
								+ '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="<%=toProfile%>" alt="">'              
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
		
		function getUnread() {
			$.ajax({
				type: "POST",
				url: "./ChatUnreadServlet",
				data: {
					userID: encodeURIComponent('<%= userID %>'),
				},
				success: function(result){
					if(result >=1){
						showUnread(result);
					}else{
						showUnread('');
					}
				}
			});
			
		}
		function getInfiniteUnread() {
			setInterval(function() {
				getUnread();
			}, 2000);
		}
		function showUnread(result) {
			$('#unread').html(result);
		}
	</script>
</head>
<body>
	<nav class="navbar navbar-default"> <!-- 디폴트로 부트스트랩의 네브바 컴포넌트를 사용한다.   -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
					<span class="icon-bar"></span> <!-- 가로 막대기 -->
					<span class="icon-bar"></span>	
					<span class="icon-bar"></span>	
					
				</button>
				<a class="navbar-brand" href="index.jsp"> 실시간 회원제 채팅 서비스 </a>
		</div>
		
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="index.jsp">메인</a></li>
				<li><a href="find.jsp">친구찾기</a></li>
				<li><a href="box.jsp">메시지함<span id="unread" class="label label-info"></span></a></li>
				<li><a href="boardView.jsp">자유게시판</a></li>
			</ul>
			<%
				if(userID != null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="update.jsp">회원정보수정</a></li>
						<li><a href="profileUpdate.jsp">프로필 수정</a></li>
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
					
				</li>
			</ul>
			
			<%
				} 
			%>
			
		</div>
	</nav>
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

	
	<!-- 모달창 생성 -->
	<% 
		String messageContent = null;
		if(session.getAttribute("messageContent") !=null) {
			messageContent = (String) session.getAttribute("messageContent");
		}
		
		String messageType = null;
		if(session.getAttribute("messageType") !=null) {
			messageType = (String) session.getAttribute("messageType");
		}

		if(messageContent != null) {
	%>	
	
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
					<div class="modal-content <% if(messageType.equals("오류메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
					<div class="modal-header panel-heading">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times</span>
									<span class="sr-only">close</span>
								</button>
							
					<h4 class="modal-title">
							<%=messageType %>
					</h4>
					</div>
					<div class="modal-body">
						<%=messageContent %>
					</div>
					<div class="modal-footer">
							<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
			</div>
		</div>
	</div>
</div>		

	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
					<div id="checkType" class="modal-content panel-info">
					<div class="modal-header panel-heading">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times</span>
									<span class="sr-only">close</span>
								</button>
							
							<h4 class="modal-title">
								확인 메시지
							</h4>
					</div>
					<div id="checkMessage" class="modal-body">
					</div>
					<div class="modal-footer">
							<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
					
			</div>
		</div>
	</div>
</div>
		
	<script>
		$('#messageModal').modal("show");

	</script>
	
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>		
	
	<script type="text/javascript">
		$(document).ready(function(){ 
		getUnread();
		chatListFunction('0');
		getInfiniteChat();
		getInfiniteUnread();});
	</script>

	

</body>
</html>