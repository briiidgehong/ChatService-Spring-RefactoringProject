package chat.service;

import chat.dao.ChatDAO;
import chat.dto.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    private ChatDAO chatDAO;

    @Autowired
    public ChatServiceImpl(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
    //리스트 가져오는 메소드
    public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
        HashMap map = new HashMap();
        map.put("fromID",fromID);
        map.put("toID",toID);
        map.put("chatID",chatID);

        List<ChatDTO> list = chatDAO.getListById(map);

        ArrayList chatList=new ArrayList<ChatDTO>(); // CHATLIST 초기화

        for(ChatDTO rs : list)
        {
            ChatDTO chat = new ChatDTO();
            chat.setChatID(rs.getChatID());
            //sql injection / cross site script 같은 공격들을 방어하기 위해 특수문자들을 치환해준다.
            chat.setFromID(rs.getFromID().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            chat.setToID(rs.getToID().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            chat.setChatContent(rs.getChatContent().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            int chatTime = Integer.parseInt(rs.getChatTime().substring(11, 13));
            String timeType = "오전";
            if(chatTime > 12) {
                timeType = "오후";
                chatTime -= 12;
            }
            chat.setChatTime(rs.getChatTime().substring(0,  11) + " " + timeType + " " + chatTime + ":" + rs.getChatTime().substring(14, 16) + "");
            chatList.add(chat);
        }
        return chatList; //List 반환
    }


    //최근 내역 가져오는 메소드
    public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number) {
        HashMap map = new HashMap();
        map.put("fromID",fromID);
        map.put("toID",toID);
        map.put("number",number);

        List<ChatDTO> list = chatDAO.getListByRecent(map);

        ArrayList chatList=new ArrayList<ChatDTO>(); // CHATLIST 초기화
        for(ChatDTO rs : list) {
            ChatDTO chat = new ChatDTO();
            chat.setChatID(rs.getChatID());
            //sql injection / cross site script 같은 공격들을 방어하기 위해 특수문자들을 치환해준다.
            chat.setFromID(rs.getFromID().replaceAll(" ", "&nbsp;").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
            chat.setToID(rs.getToID().replaceAll(" ", "&nbsp;").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
            chat.setChatContent(rs.getChatContent().replaceAll(" ", "&nbsp;").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
            int chatTime = Integer.parseInt(rs.getChatTime().substring(11, 13));
            String timeType = "오전";
            if (chatTime >= 12) {
                timeType = "오후";
                chatTime -= 12;
            }
            chat.setChatTime(rs.getChatTime().substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getChatTime().substring(14, 16) + "");
            chatList.add(chat);
        }
        return chatList; //List 반환
    }


    //전송 메소드
    public int submit(String fromID, String toID, String chatContent) {
        HashMap map = new HashMap();
        map.put("fromID",fromID);
        map.put("toID",toID);
        map.put("chatContent",chatContent);

        chatDAO.submit(map);
        return 1;
    }

    //채팅을 읽은 것으로 업데이트
    public int readChat(String fromID, String toID) {
        HashMap map = new HashMap();
        map.put("fromID",fromID);
        map.put("toID",toID);

        chatDAO.readChatUpate(map);
       return 1;
    }

    //읽지 않은 채팅의 갯수 count
    public int getAllUnreadChat(String userID) {
        int Count = chatDAO.getAllUnreadChat(userID);
        return Count;
    }

    //메시지함
    public ArrayList<ChatDTO> getBox(String userID) {
        List<ChatDTO> list = chatDAO.getBox(userID);

        ArrayList chatList = new ArrayList<ChatDTO>(); // CHATLIST 초기화

        for(ChatDTO rs : list) {
            ChatDTO chat = new ChatDTO();
            chat.setChatID(rs.getChatID());
            //sql injection / cross site script 같은 공격들을 방어하기 위해 특수문자들을 치환해준다.
            chat.setFromID(rs.getFromID().replaceAll(" ","&nbsp;").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            chat.setToID(rs.getToID().replaceAll(" ","&nbsp;").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            chat.setChatContent(rs.getChatContent().replaceAll(" ","&nbsp;").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            int chatTime = Integer.parseInt(rs.getChatTime().substring(11, 13));
            String timeType = "오전";
            if(chatTime >= 12) {
                timeType = "오후";
                chatTime -= 12;
            }
            chat.setChatTime(rs.getChatTime().substring(0,  11) + " " + timeType + " " + chatTime + ":" + rs.getChatTime().substring(14, 16) + "");
            chatList.add(chat);
        }

        for(int i=0; i< chatList.size(); i++) {
            ChatDTO x= (ChatDTO)chatList.get(i);
            for(int j=0; j<chatList.size();j++) {
                ChatDTO y = (ChatDTO)chatList.get(j);
                if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
                    if(x.getChatID() < y.getChatID()) {
                        chatList.remove(x);
                        i--;
                        break;
                    }else {
                        chatList.remove(y);
                        j--;
                    }
                }
            }
        }
        return chatList; //List 반환
    }

    //안읽은 채팅 갯수 count해서 가져오기
    public int getUnreadChat(String fromID, String toID) {
        HashMap map = new HashMap();
        map.put("fromID", fromID);
        map.put("toID", toID);

        return chatDAO.getUnreadChat(map);
    }
}
