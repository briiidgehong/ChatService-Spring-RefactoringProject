package board.dao;

import board.dto.BoardDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public class BoardDAOImpl implements BoardDAO{

	private SqlSession sqlSession;

	@Autowired
	public BoardDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public int write(HashMap map){
		return sqlSession.insert("boardMapper.write", map);
	}

	public BoardDTO getBoard(String boardID)
	{
		return sqlSession.selectOne("boardMapper.getBoard", boardID);
	}

	public List<BoardDTO> getList(HashMap map){
		return sqlSession.selectList("boardMapper.getList", map);
	}

	public int hit(String boardID)
	{
		return sqlSession.update("boardMapper.hit", boardID);
	}

	public String getFile(String boardID)
	{
		return sqlSession.selectOne("boardMapper.getFile", boardID);
	}

	public String getRealFile(String boardID)
	{
		return sqlSession.selectOne("boardMapper.getRealFile", boardID);
	}

	public int update(HashMap map)
	{
		return sqlSession.update("boardMapper.update", map);
	}

	public int delete(String boardID)
	{
		return sqlSession.update("boardMapper.delete", boardID);
	}

	public int reply(HashMap map)
	{
		return sqlSession.insert("boardMapper.reply", map);
	}

	public int replyUpdate(HashMap map)
	{
		return sqlSession.update("boardMapper.replyUpdate", map);
	}


	public List<BoardDTO> nextPage(String pageNumber)
	{
		return sqlSession.selectList("boardMapper.nextPage", pageNumber);
	}

	public int targetPage(int pageNumber)
	{
		return sqlSession.selectOne("boardMapper.targetPage", pageNumber);
	}

}
