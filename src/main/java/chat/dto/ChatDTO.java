package chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatDTO {

	int chatID;
	String fromID;
	String toID;
	String chatContent;
	String chatTime;
}


