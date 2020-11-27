<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> <!--html5를 따른다. -->
<html>


<head>
	<%@include file="/static/header.jsp"%><!-- header -->

	<script type="text/javascript">
		function chatBoxFunction() {
			var userID = '<%= userID %>';
			$.ajax({
				type: "POST",
				url: "./ChatBoxServlet",
				data: {
					userID: encodeURIComponent(userID),
				},
				success: function(data){
					if(data == "") return;
					$('#boxTable').html('');
					var parsed = JSON.parse(data);
					var result = parsed.result;
					for(var i=0; i< result.length; i++){
						if(result[i][0].value == userID) {
							result[i][0].value = result[i][1].value;
						}else {
							result[i][1].value = result[i][0].value;
						}
						addBox(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value, result[i][5].value);
					}
				}
			});
			
		}
		
		function addBox(lastID, toID, chatContent, chatTime, unread, profile) {
			$('#boxTable').append('<tr onclick="location.href=\'chat.jsp?toID=' + encodeURIComponent(toID) +'\'">' +
					'<td style="width: 150px;"><h5>' +
					'<img class="media-object img-circle" style="margin: 0 auto; max-width: 40px; max-height: 40px;" src="' + profile +   '">' +
					'<h5>' + lastID + '</h5></td>' +
					'<td>' +
					'<h5>' + chatContent + '&nbsp;&nbsp;&nbsp;' +
					'<span class="label label-info">'  + unread + '</span>' + '</h5>' +
					'<div class="pull-right">' + chatTime + '</div>' +
					'</td>' +
					'</tr>');
		}
		function getInfiniteBox() {
			setInterval(function() {
				chatBoxFunction();
			}, 3000);
		}
	</script>

	<%
		if(userID == null) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	
</head>
<body>
	<%@include file="/static/body-h.jsp"%><!-- body-h -->
	
	<div class="container">
		<table class="table" style="margin: 0 auto;">
			<thead>
				<tr>
					<th><h4>주고받은 메시지 목록</h4></th>
				</tr>
			</thead>
			<div style="overflow-y: auto; width: 100%; max-height: 450px;">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable"> 
					</tbody>
				</table>
			</div>
		</table>
	</div>

	<%@include file="/static/body-f.jsp"%><!-- body-h -->

	<%
		if(userID != null) {
	%>
	<script type="text/javascript">
		$(document).ready(function(){
			chatBoxFunction();
			getInfiniteBox();
		});
	</script>
	<%
		}
	%>
</body>
</html>