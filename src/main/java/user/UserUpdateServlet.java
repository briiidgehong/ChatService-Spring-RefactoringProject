package user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
		
		String userID = request.getParameter("userID");
		HttpSession session = request.getSession(); //세션값 검증
		String userPassword1 = request.getParameter("userPassword1");
		String userPassword2 = request.getParameter("userPassword2");
		String userName = request.getParameter("userName");
		String userAge = request.getParameter("userAge"); // 아무리 숫자라 하더라도 사용자에게 입력을 받는건 문자열 형태이므로 문자열 선언한다.
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		
		System.out.print(userID);
		System.out.print(userGender);
		System.out.print(userName);
		
		
		if(userID == null || userID.equals("") ||
			userPassword1 == null || userPassword1.equals("") ||
			userPassword2 == null || userPassword2.equals("") ||
			userName == null || userName.equals("") ||
			userAge == null || userAge.equals("") ||
			userGender == null || userGender.equals("") ||
			userEmail == null || userEmail.equals("")  ) { //프로필 사진 업데이트는 사용자 선택
				request.getSession().setAttribute("messageType", "오류메시지");
				request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
				response.sendRedirect("update.jsp");
				return;
		}
		
		if(!userID.equals((String) session.getAttribute("userID"))) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
	}
		
		if(!userPassword1.equals(userPassword2)) {
				request.getSession().setAttribute("messageType", "오류메시지");
				request.getSession().setAttribute("messageContent", "비밀번호가 다릅니다.");
				response.sendRedirect("update.jsp");
				return;
		}
		int result = new UserDAO().update(userID, userPassword1, userName, userAge, userGender, userEmail);
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent", "회원 정보 수정에 성공하였습니다.");
			response.sendRedirect("index.jsp");
		}
		else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("update.jsp");
		}
		   
		
	}

}
