package chat.dao;

import chat.dto.ChatDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.dto.UserDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//DATA ACCESS OBJECT 데이터 접근 객체 -> 실질적으로 데이터베이스에 접근해서 데이터를 가져오거나 쓰거나 하는 역할
@Repository
public class ChatDAOImpl implements ChatDAO{

	private SqlSession sqlSession;

	@Autowired
	public ChatDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}



	public List<ChatDTO> getListById(HashMap map){
		return sqlSession.selectList("chatMapper.getListByID", map);
	}

	public List<ChatDTO> getListByRecent(HashMap map){
		return sqlSession.selectList("chatMapper.getListByRecent", map);
	}

	public void submit(HashMap map){
		sqlSession.insert("chatMapper.submit", map);
	}


	public void readChatUpate(HashMap map){
		sqlSession.insert("chatMapper.readChatUpate", map);
	}

	public int getAllUnreadChat(String userID){
		return sqlSession.selectOne("chatMapper.getAllUnreadChat", userID);
	}


	public List<ChatDTO> getBox(String userID){
		return sqlSession.selectList("chatMapper.getBox", userID);
	}

	public int getUnreadChat(HashMap map){
		return sqlSession.selectOne("chatMapper.getUnreadChat", map);
	}





}
