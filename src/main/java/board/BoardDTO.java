package board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDTO {

	String userID;
	int boardID;
	String boardTitle;
	String boardContent;
	String boardDate;
	int boardHit;
	String boardFile;
	String boardRealFile;
	int boardGroup;
	int boardSequence;
	int boardLevel;
	int boardAvailable;
	
}
