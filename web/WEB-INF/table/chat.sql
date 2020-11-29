create table chat (
chatID INT PRIMARY KEY AUTO_INCREMENT,
fromID VARCHAR(20),
toID VARCHAR(20),
chatContent VARCHAR(100),
chatTime DATETIME,
chatRead INT
);