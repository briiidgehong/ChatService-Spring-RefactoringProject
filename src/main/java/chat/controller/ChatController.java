package chat.controller;

import chat.dao.ChatDAOImpl;
import chat.dto.ChatDTO;
import chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.ArrayList;

@Controller
public class ChatController {

    private ChatService chatService;
    private UserService userService;


    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }



    //chatList
    @RequestMapping(value = "/chat/list", method = RequestMethod.POST) //@Responsebody @RestController
    public void chatListViaAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("chat/list 들어옴");

        String fromID = request.getParameter("fromID");
        String toID = request.getParameter("toID");
        String listType = request.getParameter("listType");
        if(fromID == null || fromID.equals("") || toID == null || toID.equals("")
                || listType == null || listType.equals("")) {
            response.getWriter().write("");}
        else {
            try {
                HttpSession session = request.getSession(); //세션값 검증 세션값과 실제 사용자가 일치하는 경우에만 정보 제공
                if(!URLDecoder.decode(fromID,"UTF-8").equals((String) session.getAttribute("userID"))) {
                    response.getWriter().write("");
                    return;
                }
                response.getWriter().write(getID(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8"), listType));
            } catch(Exception e) {
                response.getWriter().write("");
            }
        }

    }

    public String getID(String fromID, String toID, String chatID) {
        StringBuffer result= new StringBuffer("");
        result.append("{\"result\":[");
        ArrayList<ChatDTO> chatList = chatService.getChatListByID(fromID, toID, chatID);
        if(chatList.size() == 0) return "";
        for(int i=0;i<chatList.size();i++) {
            result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
            if(i != chatList.size() -1) result.append(",");
        }
        result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
        chatService.readChat(fromID, toID); //읽음 처리
        return result.toString();
    }

    public String getTen(String fromID, String toID) {
        StringBuffer result= new StringBuffer("");
        result.append("{\"result\":[");
        ArrayList<ChatDTO> chatList = chatService.getChatListByRecent(fromID, toID, 100);
        if(chatList.size() == 0) return "";

        for(int i=0;i<chatList.size();i++) {
            result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
            if(i != chatList.size() -1) result.append(",");
        }
        result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
        chatService.readChat(fromID, toID); //읽음 처리
        return result.toString();
    }

    //chatBox
    @RequestMapping(value = "/chat/box", method = RequestMethod.POST) //@Responsebody @RestController
    public void chatBoxViaAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userID = request.getParameter("userID");
        if(userID ==null || userID.equals("")) {
            response.getWriter().write("");
        }else {
            try {
                HttpSession session = request.getSession(); //세션값 검증 세션값과 실제 사용자가 일치하는 경우에만 정보 제공
                if(!URLDecoder.decode(userID,"UTF-8").equals((String) session.getAttribute("userID"))) {
                    response.getWriter().write("");
                    return;
                }
                userID= URLDecoder.decode(userID, "UTF-8");
                response.getWriter().write(getBox(userID));
            }catch (Exception e) {
                response.getWriter().write("");
            }
        }
    }

    public String getBox(String userID) {
        StringBuffer result= new StringBuffer("");
        result.append("{\"result\":[");
        ArrayList<ChatDTO> chatList = chatService.getBox(userID);
        if(chatList.size() == 0) return "";
        for(int i=(chatList.size()-1);i>=0;i--) {
            String unread ="";
            String userProfile ="";
            if(userID.equals(chatList.get(i).getToID())) {
                unread=chatService.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
                if(unread.equals("0")) unread ="";
            }

            if(userID.equals(chatList.get(i).getToID())) {
                userProfile = userService.getProfile(chatList.get(i).getFromID());
            } else {
                userProfile = userService.getProfile(chatList.get(i).getToID());
            }
            result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"},");
            result.append("{\"value\": \"" + unread + "\"},");
            result.append("{\"value\": \"" + userProfile + "\"}]");
            if(i != 0) result.append(",");
        }
        result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
        return result.toString();
    }

    //chatSubmit
    @RequestMapping(value = "/chat/submit", method = RequestMethod.POST) //@Responsebody @RestController
    public void chatSubmitViaAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fromID = request.getParameter("fromID");
        String toID = request.getParameter("toID");
        String chatContent = request.getParameter("chatContent");
        if(fromID == null || fromID.equals("") || toID == null || toID.equals("")
                || chatContent == null || chatContent.equals("")) {
            response.getWriter().write(0);
        }
		/*
		else if(fromID.equals(toID)) {
			response.getWriter().write("-1");
		}*/

        else {
            fromID = URLDecoder.decode(fromID, "UTF-8");
            toID = URLDecoder.decode(toID, "UTF-8");

            HttpSession session = request.getSession(); //세션값 검증 세션값과 실제 사용자가 일치하는 경우에만 정보 제공
            if(!URLDecoder.decode(fromID,"UTF-8").equals((String) session.getAttribute("userID"))) {
                response.getWriter().write("");
                return;
            }

            chatContent = URLDecoder.decode(chatContent, "UTF-8");
            response.getWriter().write(chatService.submit(fromID, toID, chatContent) + "");
        }
    }

    //chatUnread
    @RequestMapping(value = "/chat/unread", method = RequestMethod.POST) //@Responsebody @RestController
    public void chatUnreadViaAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userID = request.getParameter("userID");
        if(userID ==null || userID.equals("")) {
            response.getWriter().write("0");
        }else {
            userID = URLDecoder.decode(userID, "UTF-8");

            HttpSession session = request.getSession(); //세션값 검증 세션값과 실제 사용자가 일치하는 경우에만 정보 제공
            if(!URLDecoder.decode(userID,"UTF-8").equals((String) session.getAttribute("userID"))) {
                response.getWriter().write("");
                return;
            }

            response.getWriter().write(chatService.getAllUnreadChat(userID) + "");
        }
    }

}
