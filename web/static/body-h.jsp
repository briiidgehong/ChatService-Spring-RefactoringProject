<%@ page language="java" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default"> <!-- 부트스트랩의 네브바 컴포넌트 -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                aria-expanded="false">
            <span class="icon-bar"></span> <!-- 가로 막대기 -->
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>

        </button>
        <a class="navbar-brand" href="/index"> Chat Service </a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li class="active"><a href="/index">메인</a></li>
            <li><a href="/find">친구찾기</a></li>
            <li><a href="/box">메시지함<span id="unread" class="label label-info"></span></a></li>
            <li><a href="/boardView">자유게시판</a></li>
        </ul>

        <%
            if(userID == null){
        %>

        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle"
                   data-toggle="dropdown" role="button" aria-haspopup="true"
                   aria-expanded="false">접속하기<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/login">로그인</a></li>
                    <li><a href="/join">회원가입</a></li>
                </ul>

            </li>
        </ul>

        <%
        } else {
        %>

        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle"
                   data-toggle="dropdown" role="button" aria-haspopup="true"
                   aria-expanded="false">회원관리<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/update">회원정보수정</a></li>
                    <li><a href="/profileUpdate">프로필 수정</a></li>
                    <li><a href="/logoutAction">로그아웃</a></li>
                </ul>

            </li>
        </ul>

        <%
            }
        %>


    </div>
</nav>
