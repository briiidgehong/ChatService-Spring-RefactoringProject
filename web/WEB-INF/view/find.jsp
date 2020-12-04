<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>

<head>

	<%@include file="/static/header.jsp"%><!-- header -->

	<script type="text/javascript">
		function findFunction() {
			var userID = $('#findID').val();
			$.ajax({
				type: "POST",
				url: '/user/find',
				data: {userID: userID},
				success: function(result) {
					if(result== -1){
						$('#checkMessage').html('친구를 찾을수 없습니다 !');
						$('#checkType').attr('class','modal-content panel-warning');
						failFriend();
					}else{
						
						$('#checkMessage').html('친구찾기 성공 !');
						$('#checkType').attr('class','modal-content panel-success');
						var data = JSON.parse(result);
						var profile = data.userProfile;
						getFriend(userID, profile);
						}
					$('#checkModal').modal("show");
				}
			});
			
		}
		function getFriend(findID, userProfile){
			$('#friendResult').html('<thead>'+ 
					'<tr>' +
					'<th><h4>검색 결과</h4></th>' +
					'</tr>'+
					'<thead>'+
					'<tbody>'+
					'<tr>'+
					'<td style="text-align: center;">'+
					'<img class="media-object img-circle" style="max-width: 300px; margin: 0 auto;" src="/upload/' + userProfile+ '">' +
					'<h3>' + findID + 
					'</h3><a href="/chat?toID=' + encodeURIComponent(findID) +
					'"class="btn btn-primary pull-right">'+
					'메시지 보내기</a></td>'+
					'<tr>' +
					'</tbody>');
				}
		function failFriend() {
			$('#friendResult').html('');
		}
	</script>

	<%
		if(userID == null){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어있지 않습니다.");
			response.sendRedirect("/index");
			return;

		}
	%>
</head>
<body>

	<%@include file="/static/body-h.jsp"%><!-- body-h -->

	<div class="container">
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="2"><h4>검색으로 친구찾기</h4></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width: 110px;"><h5>친구 아이디</h5></td>
					<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 아이디를 입력하세요."></td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="findFunction();">검색</button></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="container">
		<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
		</table>
	</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->

</body>
</html>