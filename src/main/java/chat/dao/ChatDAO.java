package chat.dao;

import chat.dto.ChatDTO;

import java.util.HashMap;
import java.util.List;

public interface ChatDAO {
    public List<ChatDTO> getListById(HashMap map);
    public List<ChatDTO> getListByRecent(HashMap map);
    public void submit(HashMap map);
    public void readChatUpate(HashMap map);
    public int getAllUnreadChat(String userID);
    public List<ChatDTO> getBox(String userID);
    public int getUnreadChat(HashMap map);

}
