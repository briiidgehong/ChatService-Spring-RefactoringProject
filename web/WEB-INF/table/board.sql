create table board (
userID VARCHAR(20),
boardID INT PRIMARY KEY,
boardTitle VARCHAR(50),
boardContent VARCHAR(2048),
boardDate DATETIME,
boardHit INT,
boardFile VARCHAR(100),
boardRealFile VARCHAR(100),
boardGroup INT,
boardSequence INT,
boardLevel INT,
boardAvailable INT );