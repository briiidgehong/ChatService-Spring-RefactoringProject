<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF_8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Comatible" content="ie=edge" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
  <title>Profile</title>
  <link rel="stylesheet" href="css/kakaoStyle/style.css" />
</head>

<body>
  <header class="top-header top-header--transparent">
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
        <a href="index.jsp"> <i class="fa fa-times fa-lg"></i> </a>
      </div>
      <div class="header__column"></div>
      <div class="header__column"><i class="fa fa-user fa-lg"></i></div>
    </div>
  </header>
  <main class="profile">
    <header class="profile__header">
      <div class="profile__header-container">
        <img src="images/avatar.jpg" alt="" />
        <h3 class="profile__header-title">Nicolas</h3>
      </div>
    </header>
    <div class="profile__container">
      <input type="text" placeholder="itnicolasme@gmail.com" />
      <div class="profile__actions">
        <div class="profile__action">
          <span class="profile__action-circle">
            <i class="fa fa-comment fa-lg"></i>
          </span>
          <span class="profile__action-title">My Chatroom</span>
        </div>
        <div class="profile__action">
          <span class="profile__action-circle">
            <i class="fa fa-pencil fa-lg"></i>
          </span>
          <span class="profile__action-title">Edit Profile</span>
        </div>
      </div>
    </div>
  </main>
  <div class="bigScreenText">
    <span>Please make your screen smaller</span>
  </div>
</body>

</html>