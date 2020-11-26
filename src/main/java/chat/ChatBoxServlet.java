package chat;

import user.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;


public class ChatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
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
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getBox(userID);
		if(chatList.size() == 0) return "";
		for(int i=(chatList.size()-1);i>=0;i--) {
			String unread ="";
			String userProfile ="";
			if(userID.equals(chatList.get(i).getToID())) {
				unread=chatDAO.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
				if(unread.equals("0")) unread ="";
			}
			
			if(userID.equals(chatList.get(i).getToID())) {
				userProfile = new UserDAO().getProfile(chatList.get(i).getFromID());
			} else {
				userProfile = new UserDAO().getProfile(chatList.get(i).getToID());
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

}
