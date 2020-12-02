package chat;

import chat.dao.ChatDAOImpl;
import chat.dto.ChatDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;


public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
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

		public String getTen(String fromID, String toID) {
			StringBuffer result= new StringBuffer("");
			result.append("{\"result\":["); 
			ChatDAOImpl chatDAO = new ChatDAOImpl();
			ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 100);
			if(chatList.size() == 0) return "";
			
			for(int i=0;i<chatList.size();i++) {
				result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},"); 
				result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},"); 
				result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},"); 
				result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]"); 
				if(i != chatList.size() -1) result.append(",");
			}
			result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
			chatDAO.readChat(fromID, toID); //읽음 처리
			return result.toString();
			}
		
		public String getID(String fromID, String toID, String chatID) {
			StringBuffer result= new StringBuffer("");
			result.append("{\"result\":["); 
			ChatDAOImpl chatDAO = new ChatDAOImpl();
			ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
			if(chatList.size() == 0) return "";
			for(int i=0;i<chatList.size();i++) {
				result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},"); 
				result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},"); 
				result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},"); 
				result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]"); 
				if(i != chatList.size() -1) result.append(",");
			}
			result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
			chatDAO.readChat(fromID, toID); //읽음 처리
			return result.toString();
			}
		
}
