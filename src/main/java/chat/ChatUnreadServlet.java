package chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

@Controller
public class ChatUnreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/ChatUnreadServlet")
	public void myFunction(){
		System.out.println("과연??");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("123");
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
			
			response.getWriter().write(new ChatDAO().getAllUnreadChat(userID) + "");
		}
	}

}
