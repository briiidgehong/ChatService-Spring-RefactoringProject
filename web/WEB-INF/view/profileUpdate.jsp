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
		<form method="post" action="/user/profile" enctype="multipart/form-data">
			<table class="table table=bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"><h4>프로필 사진 수정 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><h5><%= user.getUserID() %></h5>
						<input type="hidden" name="userID" value="<%= user.getUserID() %>"></td>
					</tr>
					
					<tr>
						<td style="width: 110px;"><h5>사진 업로드</h5></td>
						<td colspan="2">
						<input type="file" name="userProfile" class="file">
						<div class="input-group col-xs-12">
							<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
							<input type="text" class="form-control input-lg" disabled placeholder="이미지를 업로드하세요.">
							<span class="input-group-btn">
								<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search"></i>파일 찾기</button>
							</span>
						</div> 
						</td>
					</tr>
					
					
					<tr>
					 	<td style="text-alin: left;" colspan="3"><h5 style="color: red;"></h5><input class="btn btn-primary pull-right" type="submit" value="등록"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->

	<script type="text/javascript">
		$(document).on('click','.browse',function() {
			var file = $(this).parent().parent().parent().find('.file');
			file.trigger('click');
		});
		
		$(document).on('change', '.file', function() {
			$(this).parent().find('.form-control').val($(this).val().replace(/c:\\fakepath\\/i, ''));
		});
	</script>
	
</body>
</html>