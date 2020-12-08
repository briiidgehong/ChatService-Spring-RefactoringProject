<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF_8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Comatible" content="ie=edge" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
  <title>Chat</title>
  <link rel="stylesheet" href="css/kakaoStyle/style.css" />
</head>

<body class="body-chat">
  <header class="top-header chat-header">
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
        <a href="chats.jsp"> <i class="fa fa-chevron-left fa-lg"></i> </a>
      </div>
      <div class="header__column"><span class="header__text">Lynn</span></div>
      <div class="header__column">
        <i class="fa fa-search"></i> <i class="fa fa-bars"></i>
      </div>
    </div>
  </header>
  <main class="chat">
    <div class="date-divider">
      <span class="date-divider__text">Wednesday, August 2, 2017</span>
    </div>
    <div class="chat__message chat__message-from-me">
      <span class="chat__message-time">17:55</span>
      <span class="chat__message-body"> Hello! This is a test message. </span>
    </div>
    <div class="chat__message chat__message-to-me">
      <img src="images/avatar.jpg" class="chat_message-avatar" />
      <div class="chat__message-center">
        <h3 class="chat__message-username">Lynn</h3>
        <span class="chat__message-body"> And this is an answer </span>
      </div>
      <span class="chat__message-time">19:35</span>
    </div>
  </main>
  <div class="type-message">
    <i class="fa fa-plus fa-lg"></i>
    <div class="type-message__input">
      <input type="text" /> <i class="fa fa-smile-o fa-lg"></i>
      <span class="record-message"> <i class="fa fa-microphone fa-lg"></i> </span>
    </div>
  </div>
  <div class="bigScreenText">
    <span>Please make your screen smaller</span>
  </div>
</body>

</html>