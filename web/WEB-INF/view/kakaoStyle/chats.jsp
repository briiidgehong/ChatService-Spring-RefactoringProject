<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF_8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Comatible" content="ie=edge" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
  <title>Chats</title>
  <link rel="stylesheet" href="css/kakaoStyle/style.css" />
</head>

<body>
  <header class="top-header">
    <div class="header__top">
      <div class="header__column">
        <i class="fa fa-fighter-jet"></i>
        <!-- plane icon -->
        <i class="fa fa-wifi"></i>
        <!-- wifi icon -->
      </div>
      <div class="header__column">
        <span class="header__time">18:38</span>
      </div>
      <div class="header__column">
        <i class="fa fa-moon-o"></i>
        <!-- moon icon -->
        <i class="fa fa-bluetooth-b"></i>
        <!-- blue icon -->
        <span class="header__battery">66% <i class="fa fa-battery-full"></i>
          <!-- battery icon --></span>
      </div>
    </div>
    <div class="header__bottom">
      <div class="header__column">
        <span class="header__text">Edit</span>
      </div>
      <div class="header__column">
        <span class="header__text">Chats <i class="fa fa-caret-down"></i></span>
      </div>
      <div class="header__column">
      </div>
    </div>
  </header>
  <main class="chats">
    <div class="search-bar">
      <i class="fa fa-search"></i>
      <input type="text" placeholder="Find friends, chats, Plus Friend" />
    </div>
    <ul class="chats__list">
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">Lynn</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 15:55 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">Mason</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 13:49 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">Aiden</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 12:17 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">Lucas</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 11:38 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">Ethan</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 10:19 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">Noah</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 08:26 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg">
            <div class="chat__preview">
              <h3 class="chat__user">John</h3>
              <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time"> 05:55 </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="chat.jsp">
          <div class="chat__content">
            <img src="images/avatar.jpg" />
            <div class="chat__preview">
              <h3 class="chat__user">KakaoTalk</h3>
              <span class="chat__last-message">You logged into KakaoTalk PC</span>
            </div>
          </div>
          <span class="chat__date-time"> Jul 29 </span>
        </a>
      </li>
    </ul>
    <div class="chat-btn">
      <i class="fa fa-comment"></i>
    </div>
  </main>
  <nav class="tab-bar">
    <a href="index.jsp" class="tab-bar__tab">
      <i class="fa fa-user"></i> <span class="tab-bar__title">Friends</span>
    </a>
    <a href="chats.jsp" class="tab-bar__tab  tab-bar__tab--selected">
      <i class="fa fa-comment"></i> <span class="tab-bar__title">Chats</span>
    </a>
    <a href="find.jsp" class="tab-bar__tab">
      <i class="fa fa-search"></i> <span class="tab-bar__title">Find</span>
    </a>
    <a href="more.jsp" class="tab-bar__tab">
      <i class="fa fa-ellipsis-h"></i>
      <span class="tab-bar__title">More</span>
    </a>
  </nav>
  <div class="bigScreenText">
    <span>Please make your screen smaller</span>
  </div>
</body>

</html>