package user;

import user.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UserRegisterCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
		
		
		String userID = request.getParameter("userID"); //사용자로부터 입력을 받은 userID 를 저장
		if(userID ==null || userID.equals("")) response.getWriter().write("-1");
		response.getWriter().write(new UserDAO().registerCheck(userID)+"" ); //사용자에게 결과를 반환 , 문자열 형태로 출력할수 있도록 하기위해 공백 문자열을 추가한다.
	}

}
