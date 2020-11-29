package user;

import user.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UserFindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
String userID = request.getParameter("userID");

		if(userID == null || userID.equals("")) {
			response.getWriter().write("-1");
		} else if(new UserDAO().registerCheck(userID) == 0) {
			try {
				response.getWriter().write(find(userID));
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("-1");
			}
		} else {
			response.getWriter().write("-1");
		}
		
		
	}
	 public String find(String userID) throws Exception {
		 StringBuffer result = new StringBuffer("");
		 result.append("{\"userProfile\":\"" + new UserDAO().getProfile(userID) + "\"}");
		 return result.toString();
	 }
}
