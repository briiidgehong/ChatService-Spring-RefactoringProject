package board.service;

import board.dao.BoardDAO;
import board.dao.BoardDAOImpl;
import board.dto.BoardDTO;
import chat.dto.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    private BoardDAO boardDAO;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public int write(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile) { //게시글 등록 수행
        HashMap map = new HashMap<String, Object>();
        map.put("userID", userID);
        map.put("boardTitle", boardTitle);
        map.put("boardContent", boardContent);
        map.put("boardFile", boardFile);
        map.put("boardRealFile", boardRealFile);
        boardDAO.write(map);
        return 1;
    }

    public BoardDTO getBoard(String boardID) {

        BoardDTO returnBoard = new BoardDTO();
        BoardDTO board1 = boardDAO.getBoard(boardID);

        returnBoard.setUserID(board1.getUserID());
        returnBoard.setBoardID(board1.getBoardID());
        returnBoard.setBoardTitle(board1.getBoardTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
        returnBoard.setBoardContent(board1.getBoardContent().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
        returnBoard.setBoardDate(board1.getBoardDate().substring(0, 11));
        returnBoard.setBoardHit(board1.getBoardHit());
        returnBoard.setBoardFile(board1.getBoardFile());
        returnBoard.setBoardRealFile(board1.getBoardRealFile());
        returnBoard.setBoardGroup(board1.getBoardGroup());
        returnBoard.setBoardSequence(board1.getBoardSequence());
        returnBoard.setBoardLevel(board1.getBoardLevel());
        returnBoard.setBoardAvailable(board1.getBoardAvailable());

        return returnBoard;
    }


    public ArrayList<BoardDTO> getList(String pageNumber) {
        HashMap map = new HashMap();

        int pageParam1 = Integer.parseInt(pageNumber) * 10;
        int pageParam2 = (Integer.parseInt(pageNumber) - 1) * 10;

        map.put("pageParam1", pageParam1);
        map.put("pageParam1", pageParam2);

        List<BoardDTO> list = boardDAO.getList(map);

        ArrayList boardList = new ArrayList<BoardDTO>();
        for(BoardDTO rs : list)
        {
            BoardDTO board = new BoardDTO();
            board.setUserID(rs.getUserID());
            board.setBoardID(rs.getBoardID());
            board.setBoardTitle(rs.getBoardTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            board.setBoardContent(rs.getBoardContent().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>"));
            board.setBoardDate(rs.getBoardDate().substring(0, 11));
            board.setBoardHit(rs.getBoardHit());
            board.setBoardFile(rs.getBoardFile());
            board.setBoardRealFile(rs.getBoardRealFile());
            board.setBoardGroup(rs.getBoardGroup());
            board.setBoardSequence(rs.getBoardSequence());
            board.setBoardLevel(rs.getBoardLevel());
            board.setBoardAvailable(rs.getBoardAvailable());
            boardList.add(board);
        }
        return boardList;
    }


    // 조회수 증가
    public int hit(String boardID) {
        boardDAO.hit(boardID);
        return 1;
    }

    public String getFile(String boardID) {
        return boardDAO.getFile(boardID);
    }

    public String getRealFile(String boardID) {
        return boardDAO.getFile(boardID);
    }


    public int update(String boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile) { //게시글 등록 수행
        HashMap map = new HashMap();

        map.put("boardID", boardID);
        map.put("boardTitle", boardTitle);
        map.put("boardContent", boardContent);
        map.put("boardFile", boardFile);
        map.put("boardRealFile", boardRealFile);

        return boardDAO.update(map);
    }

    public int delete(String boardID) {
        return boardDAO.delete(boardID);
    }


    public int reply(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile, BoardDTO parent) {
        HashMap map = new HashMap();

        map.put("userID", userID);
        map.put("boardTitle", boardTitle);
        map.put("boardContent", boardContent);
        map.put("boardFile", boardFile);
        map.put("boardRealFile", boardRealFile);


        int parentBoardGroup = parent.getBoardGroup();
        int parentBoardSequence = parent.getBoardSequence() + 1;
        int parentBoardLevel = parent.getBoardLevel() + 1;


        map.put("parentBoardGroup", parentBoardGroup);
        map.put("parentBoardSequence", parentBoardSequence);
        map.put("parentBoardLevel", parentBoardLevel);

        return boardDAO.reply(map);
    }

    //부모글을 입력으로 받아서 처리
    public int replyUpdate(BoardDTO parent) {
        HashMap map = new HashMap();

        int parentBoardGroup = parent.getBoardGroup();
        int parentBoardSequence = parent.getBoardSequence();

        map.put("parentBoardGroup", parentBoardGroup);
        map.put("parentBoardSequence", parentBoardSequence);

        return boardDAO.replyUpdate(map);
    }

    public boolean nextPage(String pageNumber) {
        List<BoardDTO> list = boardDAO.nextPage(pageNumber);

        if(list.size()>0)
            return true;
        else
            return false;
    }

    public int targetPage(String pageNumber) {
        int num = boardDAO.targetPage(Integer.parseInt(pageNumber) - 1) * 10;
        //return rs.getInt(1) / 10;
        return num / 10;

    }
}
