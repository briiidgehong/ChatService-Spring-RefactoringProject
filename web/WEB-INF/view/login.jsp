<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>
<head>
	<%@include file="/static/header.jsp"%><!-- header -->
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
	<%
		if(userID != null) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","현재 로그인이 되어 있는 상태입니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	%>
</head>
<body>

	<%@include file="/static/body-h.jsp"%><!-- body-h -->

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

	<%@include file="/static/body-f.jsp"%><!-- body-h -->
</body>
</html>