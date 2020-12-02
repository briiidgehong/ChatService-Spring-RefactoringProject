package board.service;

import board.dto.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface BoardService {
    public int write(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile);
    public BoardDTO getBoard(String boardID);
    public ArrayList<BoardDTO> getList(String pageNumber);
    public int hit(String boardID);
    public String getFile(String boardID);
    public String getRealFile(String boardID);
    public int update(String boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile);
    public int delete(String boardID);
    //게시글 등록 수행
    public int reply(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile, BoardDTO parent);
    //부모글을 입력으로 받아서 처리
    public int replyUpdate(BoardDTO parent);
    public boolean nextPage(String pageNumber);
    public int targetPage(String pageNumber);
}
