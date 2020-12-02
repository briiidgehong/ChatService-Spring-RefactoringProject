package chat.service;

import chat.dto.ChatDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface ChatService {
    //리스트 가져오는 메소드
    public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID);

    //최근 내역 가져오는 메소드
    public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number);

    //전송 메소드
    public int submit(String fromID, String toID, String chatContent);

    public int readChat(String fromID, String toID);

    public int getAllUnreadChat(String userID);

    //메시지함
    public ArrayList<ChatDTO> getBox(String userID);

    public int getUnreadChat(String fromID, String toID);
}