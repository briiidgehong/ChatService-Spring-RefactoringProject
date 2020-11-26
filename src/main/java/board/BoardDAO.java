package board;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO {

	DataSource dataSource;
	
	public BoardDAO() {
		try {
			
			/*
			InitialContext initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
			*/
			
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/UserChat");
			

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int write(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile) { //게시글 등록 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "INSERT INTO BOARD SELECT ?, IFNULL((SELECT MAX(boardID) + 1 FROM BOARD), 1), ?, ?, now(), 0, ?, ?, IFNULL((SELECT MAX(boardGroup) + 1 FROM BOARD), 0), 0, 0, 1";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, userID);
					pstmt.setString(2, boardTitle);
					pstmt.setString(3, boardContent);
					pstmt.setString(4, boardFile);
					pstmt.setString(5, boardRealFile);

					return pstmt.executeUpdate();
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	public BoardDTO getBoard(String boardID) { //중복체크
		BoardDTO board = new BoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT * FROM BOARD WHERE boardID =?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, boardID);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						board.setUserID(rs.getString("userID"));
						board.setBoardID(rs.getInt("boardID"));
						board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
						board.setBoardContent(rs.getString("boardContent").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
						board.setBoardDate(rs.getString("boardDate").substring(0, 11));
						board.setBoardHit(rs.getInt("boardHit"));
						board.setBoardFile(rs.getString("boardFile"));
						board.setBoardRealFile(rs.getString("boardRealFile"));
						board.setBoardGroup(rs.getInt("boardGroup"));
						board.setBoardSequence(rs.getInt("boardSequence"));
						board.setBoardLevel(rs.getInt("boardLevel"));
						board.setBoardAvailable(rs.getInt("boardAvailable"));
						
						
					}
						
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return board; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public ArrayList<BoardDTO> getList(String pageNumber) { //중복체크
		ArrayList<BoardDTO> boardList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT * FROM BOARD WHERE boardGroup > (SELECT MAX(boardGroup) FROM BOARD) - ? AND boardGroup <= (SELECT MAX(boardGroup) FROM BOARD) - ? ORDER BY boardGroup DESC, boardSequence ASC";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, Integer.parseInt(pageNumber) * 10); 
					pstmt.setInt(2, (Integer.parseInt(pageNumber) - 1) * 10); 
					rs = pstmt.executeQuery();
					boardList = new ArrayList<BoardDTO>();
					while (rs.next()) {
						BoardDTO board = new BoardDTO();
						board.setUserID(rs.getString("userID"));
						board.setBoardID(rs.getInt("boardID"));
						board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
						board.setBoardContent(rs.getString("boardContent").replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
						board.setBoardDate(rs.getString("boardDate").substring(0, 11));
						board.setBoardHit(rs.getInt("boardHit"));
						board.setBoardFile(rs.getString("boardFile"));
						board.setBoardRealFile(rs.getString("boardRealFile"));
						board.setBoardGroup(rs.getInt("boardGroup"));
						board.setBoardSequence(rs.getInt("boardSequence"));
						board.setBoardLevel(rs.getInt("boardLevel"));
						board.setBoardAvailable(rs.getInt("BoardAvailable"));
						boardList.add(board);
						
						
					}
						
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return boardList; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public int hit(String boardID) { // 조회수 증가
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL= "UPDATE BOARD SET boardHit = boardHit + 1 WHERE boardID = ?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, boardID);

					return pstmt.executeUpdate();
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	public String getFile(String boardID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT boardFile FROM BOARD WHERE boardID = ? ";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, boardID);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						return rs.getString("boardFile");
						
						
					}
					return "";	
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return ""; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	public String getRealFile(String boardID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT boardRealFile FROM BOARD WHERE boardID = ? ";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, boardID);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						return rs.getString("boardRealFile");
						
						
					}
					return "";	
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return ""; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public int update(String boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile) { //게시글 등록 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "UPDATE BOARD SET boardTitle =?, boardContent =?, boardFile =?, boardRealFile =? WHERE boardID =?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, boardTitle);
					pstmt.setString(2, boardContent);
					pstmt.setString(3, boardFile);
					pstmt.setString(4, boardRealFile);
					pstmt.setInt(5, Integer.parseInt(boardID));

					return pstmt.executeUpdate();
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public int delete(String boardID) { //게시글 등록 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "UPDATE BOARD SET boardAvailable = 0 WHERE boardID =?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);

					pstmt.setInt(1, Integer.parseInt(boardID));

					return pstmt.executeUpdate();
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public int reply(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile, BoardDTO parent) { //게시글 등록 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "INSERT INTO BOARD SELECT ?, IFNULL((SELECT MAX(boardID) + 1 FROM BOARD), 1), ?, ?, now(), 0, ?, ?, ?, ?, ?, 1";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, userID);
					pstmt.setString(2, boardTitle);
					pstmt.setString(3, boardContent);
					pstmt.setString(4, boardFile);
					pstmt.setString(5, boardRealFile);
					pstmt.setInt(6, parent.getBoardGroup());
					pstmt.setInt(7, parent.getBoardSequence() + 1);
					pstmt.setInt(8, parent.getBoardLevel() + 1);

					return pstmt.executeUpdate();
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	//부모글을 입력으로 받아서 처리
	public int replyUpdate(BoardDTO parent) { //게시글 등록 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "UPDATE BOARD SET boardSequence = boardSequence + 1 WHERE boardGroup = ? AND boardSequence > ?" ;
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setInt (1, parent.getBoardGroup());
					pstmt.setInt(2, parent.getBoardSequence());


					return pstmt.executeUpdate();
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public boolean nextPage(String pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT * FROM BOARD WHERE boardGroup >= ? ";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						return true;
						
						
					}
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return false; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public int targetPage(String pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT COUNT(boardGroup) FROM BOARD WHERE boardGroup > ? ";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, (Integer.parseInt(pageNumber) - 1 ) * 10);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						return rs.getInt(1) / 10;
						
						
					}
			}	catch(Exception e) {
					e.printStackTrace();
				} finally { // 다 썼으면 자원해제
							try {
								if(rs !=null) rs.close();
								if(pstmt !=null) pstmt.close();
								if(conn !=null) conn.close();
								} catch(Exception e) {
									e.printStackTrace();
								}
						  }
		return 0; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
}
