<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<!-- ajax 활용 -->
	<script type="text/javascript">
		function registerCheckFunction() {
			var userID = $('#userID').val(); // id가 userID 인 것의 value값을 가져와서 userID 변수에 넣어준다.
			/*ajax 를 활용한 비동기 통신 -> POST방식으로, 해당 SERVLET 으로 userID 를 보낸다. data: {userID: userID} ->속성 이름이 USERID, 값이 userID
			success 성공적으로 값을 보냈다면 result에 return 값이 담긴다.*/
			$.ajax({
							type: 'POST',
							url: './UserRegisterCheckServlet',
							data: {userID : userID},
							success: function(result){
								if(result == 1) {
									$('#checkMessage').html('사용할 수 있는 아이디 입니다.');
									$('#checkType').attr('class', 'modal-content panel-success');
								}
								else {
									$('#checkMessage').html('사용할 수 없는 아이디 입니다.');
									$('#checkType').attr('class', 'modal-content panel-warning');
								
								}
								$('#checkModal').modal("show");
							}
						});
		}
		
		
		function passwordCheckFunction() {
			var userPassword1 = $('#userPassword1').val();
			var userPassword2 = $('#userPassword2').val();
			if(userPassword1 != userPassword2) {
				$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');			
			} else {
				$('#passwordCheckMessage').html('');		
			}
			
			
		}
	</script>
</head>
<body>
	<% 
		String userID=null; //세션관리
		if(session.getAttribute("userID") != null) { // 세션값이 존재하지 않는다면
			userID = (String) session.getAttribute("userID"); // 가져와라
		}
		if(userID != null) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","현재 로그인이 되어 있는 상태입니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	%>
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
		
		<div class="collapse navbar-collase" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="index.jsp">메인</a></li>
				<li><a href="find.jsp">친구찾기</a></li>
				<li><a href="box.jsp">메시지함<span id="unread" class="label label-info"></span></a></li>
				<li><a href="boardView.jsp">자유게시판</a></li>
			</ul>
			
			<%
				if(userID==null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="active"><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
					
				</li>
			</ul>
			<%
				}
			%>
		
			
		</div>
	</nav>
	<div class="container">
		<form method="post" action="./UserLoginServlet">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"><h4>로그인 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><input class="form-control" type="text" name="userID"
							maxlength="20" placeholder="아이디를 입력하세요."></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>비밀번호</h5></td>
						<td><input class="form-control" type="text"
							name="userPassword" maxlength="20" placeholder="비밃번호를 입력하세요."></td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="2"><input
							class="btn btn-primary pull-right" type="submit" value="로그인"></td>
					</tr>
				</tbody>
			</table>
		</form>
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
									<span aria-hidden="true">&times;</span>
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
	<script>
		$('#messageModal').modal("show");
	</script>
	
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>		
	
	
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
</body>
</html>