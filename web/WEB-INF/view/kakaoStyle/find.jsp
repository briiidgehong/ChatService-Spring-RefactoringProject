<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF_8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Comatible" content="ie=edge" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
  <title>Find</title>
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
      <div class="header__column"><span class="header__text">Edit</span></div>
      <div class="header__column"><span class="header__text">Find</span></div>
      <div class="header__column"></div>
    </div>
  </header>
  <main class="find">
    <section class="find__options">
      <div class="find__option">
        <i class="fa fa-address-book fa-lg"></i>
        <span class="find__option-title">Find</span>
      </div>
      <div class="find__option">
        <i class="fa fa-qrcode fa-lg"></i>
        <span class="find__option-title">QR Code</span>
      </div>
      <div class="find__option">
        <i class="fa fa-mobile fa-lg"></i>
        <span class="find__option-title">Shake</span>
      </div>
      <div class="find__option">
        <i class="fa fa-envelope-o fa-lg"></i>
        <span class="find__option-title">Invite via SMS</span>
      </div>
    </section>
    <section class="find__recommended">
      <header>
        <h6 class="find__title">Recommended Friends</h6>
      </header>
      <div class="recommended__none">
        <span class="recommended__text">You have no recommended friends.</span>
      </div>
    </section>
  </main>
  <nav class="tab-bar">
    <a href="index.jsp" class="tab-bar__tab">
      <i class="fa fa-user"></i> <span class="tab-bar__title">Friends</span>
    </a>
    <a href="chats.jsp" class="tab-bar__tab">
      <i class="fa fa-comment"></i> <span class="tab-bar__title">Chats</span>
    </a>
    <a href="find.jsp" class="tab-bar__tab  tab-bar__tab--selected">
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