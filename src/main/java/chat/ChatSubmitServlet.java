package chat;

import chat.dao.ChatDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;


public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
		
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
			response.getWriter().write(new ChatDAO().submit(fromID, toID, chatContent) + "");
		}
	}

}
