package user.dao;

import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import user.dto.UserDTO;
import org.apache.ibatis.session.SqlSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

@Repository
//DATA ACCESS OBJECT 데이터 접근 객체 -> 실질적으로 데이터베이스에 접근해서 데이터를 가져오거나 쓰거나 하는 역할
public class UserDAOImpl implements UserDAO{

	private SqlSession sqlSession;

	@Autowired
	public UserDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}


	public UserDTO getUserById(String userID){
		return sqlSession.selectOne("userMapper.getUser", userID);
	}

	public int getCheckById(String userID){
		return sqlSession.selectOne("userMapper.getCheck", userID);
	}

	public void insertUser(UserDTO user){
		sqlSession.selectOne("userMapper.insertUser", user);
	}

	public void updateUser(UserDTO user){
		sqlSession.selectOne("userMapper.updateOne", user);
	}

	public int updateProfile(HashMap map){
		return sqlSession.update("userMapper.updateProfile", map);
	}
}
