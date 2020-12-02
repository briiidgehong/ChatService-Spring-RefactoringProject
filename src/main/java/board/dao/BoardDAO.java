package board.dao;

import board.dto.BoardDTO;
import chat.dto.ChatDTO;

import java.util.HashMap;
import java.util.List;

public interface BoardDAO {
    public int write(HashMap map);
    public BoardDTO getBoard(String boardID);
    public List<BoardDTO> getList(HashMap map);
    public int hit(String boardID);
    public String getFile(String boardID);
    public String getRealFile(String boardID);
    public int update(HashMap map);
    public int delete(String boardID);
    public int reply(HashMap map);
    public int replyUpdate(HashMap map);
    public List<BoardDTO> nextPage(String pageNumber);
    public int targetPage(int pageNumber);
}
