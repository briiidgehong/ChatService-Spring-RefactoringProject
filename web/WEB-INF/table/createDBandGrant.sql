show databases;
create databases chattingApp
show databases;

//생성한 DB에 계정 생성 및 권한 부여
CREATE USER briiidgehong@localhost  IDENTIFIED BY '0000';
GRANT ALL PRIVILEGES ON chattingapp.* TO briiidgehong@localhost WITH GRANT OPTION;

use chattingapp;

//create query
//insert query