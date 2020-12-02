<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.dto.UserDTO" %>
<%@ page import="user.dao.UserDAOImpl" %>


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

		UserDTO user = new UserDAOImpl().getUser(userID);
	%>
	<script type="text/javascript">
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
	<%@include file="/static/body-h.jsp"%><!-- body-h -->


	<div class="container">
			<form method="post" action="./UserUpdateServlet">
				<table class="table table=bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2"><h4>회원 정보 수정 양식</h4></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="width: 110px;"><h5>아이디</h5></td>
							<td><h5><%= user.getUserID() %></h5>
							<input type="hidden" name="userID" value="<%= user.getUserID() %>"></td>
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
							<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userName" type="text"  name="userName" maxlength="20" placeholder="이름을 입력하세요." value="<%= user.getUserName() %>" ></td>
						</tr>

						<tr>
							<td style="width: 110px;"><h5>나이</h5></td>
							<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userAge" type="number"  name="userAge" maxlength="20" placeholder="나이를 입력하세요." value="<%= user.getUserAge() %>" ></td>
						</tr>

						<tr>
							<td style="width: 110px;"><h5>성별</h5></td>
							<td colspan="2">
								<div class="form-group" style="ext-align: conter; margin: 0 auto;">
									<div class="btn-group" data-toggle="buttons">
										<label class="btn btn-primary <%if(user.getUserGender().equals("남자")) out.print("active"); %>">
											<input type="radio" name="userGender" autocomplete="off" value="남자" <%if(user.getUserGender().equals("남자")) out.print("checked"); %>>남자
										</label>

										<label class="btn btn-primary <%if(user.getUserGender().equals("여자")) out.print("active"); %>">
											<input type="radio" name="userGender" autocomplete="off" value="여자" <%if(user.getUserGender().equals("여자")) out.print("checked"); %>>여자
										</label>
									</div>
								</div>
							</td>
						</tr>

						<tr>
							<td style="width: 110px;"><h5>이메일</h5></td>
							<td colspan="2"><input onkeyup="passwordCheckFunction()" class="form-control" id="userEmail" type="email"  name="userEmail" maxlength="20" placeholder="이메일을 입력하세요." value="<%= user.getUserEmail() %>"></td>
						</tr>
						<tr>
							<td style="text-alin: left;" colspan="3"><h5 style="color: red;" id="passwordCheckMessage"></h5><input class="btn btn-primary pull-right" type="submit" value="수정"></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->

</body>
</html>