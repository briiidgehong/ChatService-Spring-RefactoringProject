package user;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//DATA ACCESS OBJECT 데이터 접근 객체 -> 실질적으로 데이터베이스에 접근해서 데이터를 가져오거나 쓰거나 하는 역할
public class UserDAO {

	//커넥션풀 이용
	DataSource dataSource;
	
	public UserDAO() {
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

	public int login(String userID, String userPassword) { //로그인 
		Connection conn = null;
		PreparedStatement pstmt = null; // sql injection 공격을 막아주고, 안정적으로 데이터베이스와 통신하기 위해 사용
		ResultSet rs = null;
		String SQL= "SELECT * FROM USER WHERE userID =?";
		try { //실제 하고싶은 코드 들어간다.
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString("userPassword").equals(userPassword)) { return 1; // 로그인에 성공
					}
					else {return 2; // 비밀번호가 틀림
					}
				}
				
				else {
					return 0; // 해당 사용자가 존재하지 않음
				}
				
			}catch(Exception e) {
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

	
	
	public int registerCheck(String userID) { //중복체크
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT * FROM USER WHERE userID =?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userID);
					rs = pstmt.executeQuery();
					if(rs.next() || userID.equals("")) { //아이디가 존재하거나 공백인 경우 이미 존재하는 회원이라고 알려준다.
				
						return 0; // 이미 존재하는 회원
						
					} else {
					
						return 1; // 가입 가능한 회원 아이디
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
		System.out.println("-1");
		return -1; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	
	public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile) { //회원가입 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "INSERT INTO USER VALUES(?,?,?,?,?,?,?)";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, userID);
					pstmt.setString(2, userPassword);
					pstmt.setString(3, userName);
					pstmt.setInt(4, Integer.parseInt(userAge));
					pstmt.setString(5, userGender);
					pstmt.setString(6, userEmail);
					pstmt.setString(7, userProfile);
					
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
	
	public UserDTO getUser(String userID) { //중복체크
		UserDTO user = new UserDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT * FROM USER WHERE userID =?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userID);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						user.setUserID(userID);
						user.setUserPassword(rs.getString("userPassword"));
						user.setUserName(rs.getString("userName"));
						user.setUserAge(rs.getInt("userAge"));
						user.setUserGender(rs.getString("userGender"));
						user.setUserEmail(rs.getString("userEmail"));
						user.setUserProfile(rs.getString("userProfile"));
						
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
		return user; //데이터베이스 오류가 발생한 경우 알려준다.
	}
	
	public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail) { //회원가입 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "UPDATE USER SET userPassword=?, userName=?, userAge=?, userGender=?, userEmail=? WHERE userID=?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, userPassword);
					pstmt.setString(2, userName);
					pstmt.setInt(3, Integer.parseInt(userAge));
					pstmt.setString(4, userGender);
					pstmt.setString(5, userEmail);
					pstmt.setString(6, userID);
					
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
	
	public int profile(String userID, String userProfile) { //회원가입 수행
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL= "UPDATE USER SET userProfile=? WHERE userID=?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);

					pstmt.setString(1, userProfile);
					pstmt.setString(2, userID);
					
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
	
	public String getProfile(String userID) { //중복체크
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL= "SELECT userProfile FROM USER WHERE userID =?";
		try { //실제 하고싶은 코드 들어간다.
					conn = dataSource.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userID);
					rs = pstmt.executeQuery();
					if(rs.next() || userID.equals("")) { //아이디가 존재하거나 공백인 경우 이미 존재하는 회원이라고 알려준다.
						if(rs.getString("userProfile").equals("")) {
							return "http://localhost:8080/UserChat/images/icon.PNG";
						}
						return "http://localhost:8080/UserChat/upload/" + rs.getString("userProfile");
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
		return "http://localhost:8080/UserChat/images/icon.PNG";
	}
	
}
