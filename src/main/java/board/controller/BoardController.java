package board.controller;

import board.dao.BoardDAOImpl;
import board.dto.BoardDTO;
import board.service.BoardService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //BoardDelete
    @RequestMapping(value = "/BoardDelete", method = RequestMethod.GET) //@Responsebody @RestController
    public ModelAndView BoardDelete(HttpServletRequest request, ModelAndView mView) throws Exception {

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String boardID = request.getParameter("boardID");

        if (boardID == null || boardID.equals("")) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            mView.setViewName("index");
            return mView;
        }

        BoardDTO board = boardService.getBoard(boardID);
        if (!userID.equals(board.getUserID())) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "삭제 권한이 없습니다.");
            mView.setViewName("index");
            return mView;
        }

        String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");
        String prev = boardService.getRealFile(boardID);
        int result = boardService.delete(boardID);
        if (result == -1) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "삭제 권한이 없습니다.");
            mView.setViewName("index");
            return mView;
        } else {
            File prevFile = new File(savePath + "/" + prev);
            if (prevFile.exists()) {
                prevFile.delete();
            }
            request.getSession().setAttribute("messageType", "성공 메시지");
            request.getSession().setAttribute("messageContent", "삭제에 성공했습니다.");
            mView.setViewName("boardView");
            return mView;
        }
    }

    //BoardReply
    @RequestMapping(value = "/BoardReply", method = RequestMethod.POST) //@Responsebody @RestController
    public ModelAndView BoardReply(HttpServletRequest request, ModelAndView mView) throws Exception {
        MultipartRequest multi = null;
        int fileMaxSize = 10 * 1024 * 1024;
        String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");

        try {

            multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());

        } catch (Exception e) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "파일 크기는 10MB 를 넘을 수 없습니다.");
            mView.setViewName("profileUpdate");
            return mView;
        }

        String userID = multi.getParameter("userID");
        HttpSession session = request.getSession(); //세션값 검증
        if (!userID.equals((String) session.getAttribute("userID"))) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            mView.setViewName("index");
            return mView;
        }
        String boardID = multi.getParameter("boardID");
        if (boardID == null || boardID.equals("")) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            mView.setViewName("index");
            return mView;
        }
        String boardTitle = multi.getParameter("boardTitle");
        String boardContent = multi.getParameter("boardContent");
        if (boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "내용을 모두 채워주세요.");
            mView.setViewName("index");
            return mView;
        }

        String boardFile = "";
        String boardRealFile = "";
        File file = multi.getFile("boardFile");
        if (file != null) {
            boardFile = multi.getOriginalFileName("boardFile");
            boardRealFile = file.getName();

        }

        BoardDTO parent = boardService.getBoard(boardID);
        boardService.replyUpdate(parent);
        boardService.reply(userID, boardTitle, boardContent, boardFile, boardRealFile, parent);
        session.setAttribute("messageType", "성공 메시지");
        session.setAttribute("messageContent", "성공적으로 답변이 작성되었습니다.");
        mView.setViewName("boardView");
        return mView;


    }

    //BoardUpdate
    @RequestMapping(value = "/BoardUpdate", method = RequestMethod.POST) //@Responsebody @RestController
    public ModelAndView BoardUpdate(HttpServletRequest request, ModelAndView mView) throws Exception {

        MultipartRequest multi = null;
        int fileMaxSize = 10 * 1024 * 1024;
        String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");

        try {

            multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());

        } catch (Exception e) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "파일 크기는 10MB 를 넘을 수 없습니다.");
            mView.setViewName("index");
            return mView;
        }


        String userID = multi.getParameter("userID");
        HttpSession session = request.getSession(); //세션값 검증
        if (!userID.equals((String) session.getAttribute("userID"))) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            mView.setViewName("index");
            return mView;

        }

        String boardID = multi.getParameter("boardID");
        if (boardID == null || boardID.equals("")) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            mView.setViewName("index");
            return mView;
        }


        BoardDTO board = boardService.getBoard(boardID);
        if (!userID.equals(board.getUserID())) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "수정 권한이 없습니다.");
            mView.setViewName("index");
            return mView;
        }

        String boardTitle = multi.getParameter("boardTitle");
        String boardContent = multi.getParameter("boardContent");
        if (boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "내용을 모두 채워주세요.");
            mView.setViewName("boardWrite");
            return mView;
        }

        String boardFile = "";
        String boardRealFile = "";
        File file = multi.getFile("boardFile");
        if (file != null) {
            boardFile = multi.getOriginalFileName("boardFile");
            boardRealFile = file.getName();
            String prev = boardService.getRealFile(boardID);
            File prevFile = new File(savePath + "/" + prev);
            if (prevFile.exists()) {
                prevFile.delete();
            }

        } else {
            boardFile = boardService.getFile(boardID);
            boardRealFile = boardService.getRealFile(boardID);
        }
        boardService.update(boardID, boardTitle, boardContent, boardFile, boardRealFile);
        session.setAttribute("messageType", "성공 메시지");
        session.setAttribute("messageContent", "성공적으로 게시물이 수정되었습니다.");
        mView.setViewName("boardView");
        return mView;
    }


    //BoardWrite
    @RequestMapping(value = "/BoardWrite", method = RequestMethod.POST) //@Responsebody @RestController
    public ModelAndView BoardWrite(HttpServletRequest request, ModelAndView mView) throws Exception {

        MultipartRequest multi = null;
        int fileMaxSize = 10 * 1024 * 1024;
        String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");

        try {

            multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());

        } catch (Exception e) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "파일 크기는 10MB 를 넘을 수 없습니다.");
            mView.setViewName("profileUpdate");
            return mView;
        }

        String userID = multi.getParameter("userID");
        HttpSession session = request.getSession(); //세션값 검증
        if(!userID.equals((String) session.getAttribute("userID"))) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent","접근할 수 없습니다.");
            mView.setViewName("index");
            return mView;
        }

        String boardTitle = multi.getParameter("boardTitle");
        String boardContent = multi.getParameter("boardContent");
        if(boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent","내용을 모두 채워주세요.");
            mView.setViewName("boardWrite");
            return mView;
        }

        String boardFile="";
        String boardRealFile="";
        File file = multi.getFile("boardFile");
        if(file != null) {
            boardFile = multi.getOriginalFileName("boardFile");
            boardRealFile=file.getName();

        }

        boardService.write(userID, boardTitle, boardContent, boardFile, boardRealFile);
        session.setAttribute("messageType", "성공 메시지");
        session.setAttribute("messageContent","성공적으로 게시물이 작성되었습니다.");
        mView.setViewName("boardView");
        return mView;
    }

}
