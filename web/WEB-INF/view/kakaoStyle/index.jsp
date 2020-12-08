<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF_8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Comatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Friends</title>
    <link rel="stylesheet" href="css/kakaoStyle/style.css" />
</head>

<body>
    <header class="top-header">
        <div class="header__top">
            <div class="header__column">
                <i class="fa fa-fighter-jet"></i> <!-- plane icon -->
                <i class="fa fa-wifi"></i> <!-- wifi icon -->
            </div>
            <div class="header__column">
                <span class="header__time">18:38</span>
            </div>
            <div class="header__column">
                <i class="fa fa-moon-o"></i> <!-- moon icon -->
                <i class="fa fa-bluetooth-b"></i> <!-- blue icon -->
                <span class="header__battery">66% <i class="fa fa-battery-full"></i> <!-- battery icon --></span>
            </div>
        </div>
        <div class="header__bottom">
            <div class="header__column">
                <span class="header__text">Manage</span>
            </div>
            <div class="header__column">
                <span class="header__text">Friends <span class="header__number">1</span></span>
            </div>
            <div class="header__column">
                <i class="fa fa-cog fa-lg"></i>
            </div>
        </div>
    </header>
    <main class="friends">
        <div class="search-bar">
            <i class="fa fa-search"></i>
            <input type="text" placeholder="Find friends, chats, Plus Friend">
        </div>
        <section class="friends__section">
            <header class="friends__section-header">
                <h6 class="friends__section-title">My profile</h6>
            </header>
            <div class="friends__section-rows">
                <div class="friends__section-row">
                    <img src="images/avatar.jpg" alt="">
                    <a href="profile.jsp" class="friends__section-name">
                        Nicolas
                    </a>
                </div>
                <div class="friends__section-row">
                    <img src="images/avatar.jpg" alt="">
                    <span class="friends__section-name">Friends'Names Display</span>
                </div>
        </section>
        <section class="friends__section">
            <header class="friends__section-header">
                <h6 class="friends__section-title"></h6>
            </header>
            <div class="friends__section-rows">
                <div class="friends__section-row with-tagline">
                    <div class="friends__section-column">
                        <img src="images/avatar.jpg" alt="">
                        <span class="friends__section-name">Nicolas</span>
                    </div>
                    <span class="friends__section-tagline">
                        Life is short. So live your life.
                    </span>
                </div>
            </div>
        </section>
    </main>
    <nav class="tab-bar">
        <a href="index.jsp" class="tab-bar__tab tab-bar__tab--selected">
            <i class="fa fa-user"></i>
            <span class="tab-bar__title">Friends</span>
        </a>
        <a href="chats.jsp" class="tab-bar__tab">
            <i class="fa fa-comment"></i>
            <span class="tab-bar__title">Chats</span>
        </a>
        <a href="find.jsp" class="tab-bar__tab">
            <i class="fa fa-search"></i>
            <span class="tab-bar__title">Find</span>
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