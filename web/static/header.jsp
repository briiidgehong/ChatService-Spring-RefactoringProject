<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String userID = null; //세션관리
    if (session.getAttribute("userID") != null) { // 세션값이 존재한다면
        userID = (String) session.getAttribute("userID"); // 가져와라
    }
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1"> <!-- 부트스트랩을 넣었을때 반응형 웹이 잘 출력될수 있도록 뷰포트를 넣어준다.  -->
<link rel="stylesheet" href="/css/bootstrap.css">
<link rel="stylesheet" href="/css/custom.css"> <!--css 파일 불러오기  -->

<title>Chatting Service</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> <!--ajax 를 위해서 공식사이트에서 제공하는 제이쿼리를 링크로 가져온다.  -->
<script src="/js/bootstrap.js"></script> <!--마찬가지로 우리가 받았던 부트스트랩 안의 js 페이지도 가져온다. -->
<script type="text/javascript">
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
