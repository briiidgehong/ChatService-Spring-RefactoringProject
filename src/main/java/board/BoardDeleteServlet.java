package board;

import board.dao.BoardDAO;
import board.dto.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //인코딩 설정 -> 한글처리 가능
		response.setContentType("text/html;charset=UTF-8"); //인코딩 설정 -> 한글처리 가능
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		String boardID = request.getParameter("boardID");
		if(boardID == null || boardID.equals("")) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
			
		}
		
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO board = boardDAO.getBoard(boardID);
		if(!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","삭제 권한이 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	
		String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");
		String prev = boardDAO.getRealFile(boardID);
		int result = boardDAO.delete(boardID);
		if(result == -1) {
			session.setAttribute("messageType", "오류메시지");
			session.setAttribute("messageContent","삭제 권한이 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		} else {
			File prevFile = new File (savePath + "/" + prev);
			if(prevFile.exists()) {
				prevFile.delete();
			}
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "삭제에 성공했습니다.");
			response.sendRedirect("boardView.jsp");
		}
		
	}

}
