package user;

import user.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
		String userID = request.getParameter("userID"); //사용자로부터 입력을 받은 userID 를 저장
		String userPassword = request.getParameter("userPassword"); 
		
		if(userID==null || userID.equals("")|| userPassword==null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요.");
			response.sendRedirect("login.jsp");
			return;
		}
		
		int result = new UserDAO().login(userID, userPassword);
		if(result == 1) {
			request.getSession().setAttribute("userID",userID);
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
			response.sendRedirect("index.jsp");
		}
		if(result == 2) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "비밀번호를 확인하세요.");
			response.sendRedirect("login.jsp");
		}
		if(result == 0) {
			request.getSession().setAttribute("messageType", "오루메시지");
			request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
			response.sendRedirect("login.jsp");
		}
		if(result == -1) {
			request.getSession().setAttribute("messageType", "오루메시지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("login.jsp");
		}
	}

}
