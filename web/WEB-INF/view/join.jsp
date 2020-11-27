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
		<form method="post" action="./UserRegisterServlet">
			<table class="table table=bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>회원 등록 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><input class="form-control" type="text" id="userID" name="userID" maxlength="20" placeholder="아이디를 입력하세요."></td>
						<td style="width: 110px;"><button "btn btn-primary" onclick="registerCheckFunction();" type="button">중복체크</button></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>비밀번호</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userPassword1" type="password"  name="userPassword1" maxlength="20" placeholder="비밀번호를 입력하세요."></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>비밀번호 확인</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userPassword2" type="password"  name="userPassword2" maxlength="20" placeholder="비밀번호 확인을 입력하세요."></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>이름</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userName" type="text"  name="userName" maxlength="20" placeholder="이름을 입력하세요."></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>나이</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userAge" type="number"  name="userAge" maxlength="20" placeholder="나이를 입력하세요."></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>성별</h5></td>
						<td colspan="2">
							<div class="form-group" style="ext-align: conter; margin: 0 auto;">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-primary">
										<input type="radio" name="userGender" autocomplete="off" value="남자" >남자
									</label>

									<label class="btn btn-primary">
										<input type="radio" name="userGender" autocomplete="off" value="여자" >여자
									</label>
								</div>
							</div>
						</td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>이메일</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userEmail" type="email"  name="userEmail" maxlength="20" placeholder="이메일을 입력하세요."></td>
					</tr>
					<tr>
						<td style="text-alin: left;" colspan="3"><h5 style="color: red;" id="passwordCheckMessage"></h5><input class="btn btn-primary pull-right" type="submit" value="등록"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->
</body>
</html>