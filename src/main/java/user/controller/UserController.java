package user.controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import user.dao.UserDAOImpl;
import user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //userFind
    @RequestMapping(value = "/user/find", method = RequestMethod.POST) //@Responsebody @RestController
    public void userFindviaAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("user/find 들어옴");
        String userID = request.getParameter("userID");
        if (userID == null || userID.equals("")) {
            response.getWriter().write(this.find(userID));
        } else response.getWriter().write("-1");

    }

    public String find(String userID) throws Exception {
        StringBuffer result = new StringBuffer("");
        result.append("{\"userProfile\":\"" + userService.getProfile(userID) + "\"}");
        return result.toString();
    }

    //userLogin
    @RequestMapping(value = "/user/login", method = RequestMethod.POST) //@Responsebody @RestController
    public ModelAndView userLogin(HttpServletRequest request, ModelAndView mView) throws Exception {
        String userID = request.getParameter("userID"); //사용자로부터 입력을 받은 userID 를 저장
        String userPassword = request.getParameter("userPassword");

        if (userID == null || userID.equals("") || userPassword == null || userPassword.equals("")) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요.");
            mView.setViewName("login");
            return mView;
        }

        int result = userService.login(userID, userPassword);
        if (result == 1) {
            request.getSession().setAttribute("userID", userID);
            request.getSession().setAttribute("messageType", "성공메시지");
            request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
            mView.setViewName("index");
            return mView;
        }
        if (result == 2) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "비밀번호를 확인하세요.");
            mView.setViewName("login");
            return mView;
        }
        if (result == 0) {
            request.getSession().setAttribute("messageType", "오류메시지");
            mView.setViewName("login");
            return mView;
        }
        if (result == -1) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
            mView.setViewName("login");
            return mView;

        }
        return mView;
    }

    //userProfile
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST) //@Responsebody @RestController
    public void userProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartRequest multi = null;
        int fileMaxSize = 10 * 1024 * 1024;
        String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");

        try {

            multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());

        } catch (Exception e) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "파일 크기는 10MB 를 넘을 수 없습니다.");
            response.sendRedirect("profileUpdate.jsp");
            return;
        }

        String userID = multi.getParameter("userID");
        HttpSession session = request.getSession(); //세션값 검증
        if (!userID.equals((String) session.getAttribute("userID"))) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;
        }

        String fileName = "";
        File file = multi.getFile("userProfile");
        if (file != null) {
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
                String prev = userService.getUser(userID).getUserProfile();
                File prevFile = new File(savePath + "/" + prev);
                if (prevFile.exists()) {
                    prevFile.delete();
                }
                fileName = file.getName();
            } else {
                if (file.exists()) {
                    file.delete();
                }
                session.setAttribute("messageType", "오류메시지");
                session.setAttribute("messageContent", "이미지 파일만 업로드 가능합니다.");
                response.sendRedirect("profileUpdate.jsp");
                return;
            }

        }

        userService.profile(userID, fileName);
        session.setAttribute("messageType", "성공 메시지");
        session.setAttribute("messageContent", "성공적으로 프로필이 변경되었습니다.");
        response.sendRedirect("index.jsp");
        return;
    }


    //userRegisterCheck
    @RequestMapping(value = "/user/registercheck", method = RequestMethod.POST) //@Responsebody @RestController
    public void userRegisterCheckViaAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("registercheck 들어옴");

        String userID = request.getParameter("userID"); //사용자로부터 입력을 받은 userID 를 저장
        if (userID == null || userID.equals("")) {
            response.getWriter().write("-1");
        } else {
            System.out.println(userService.registerCheck(userID));
            response.getWriter().write(userService.registerCheck(userID) + ""); //사용자에게 결과를 반환 , 문자열 형태로 출력할수 있도록 하기위해 공백 문자열을 추가한다.
        }
    }




    //userRegister
    @RequestMapping(value = "/user/register", method = RequestMethod.POST) //@Responsebody @RestController
    public ModelAndView userRegister(HttpServletRequest request, ModelAndView mView) throws Exception {

        String userID = request.getParameter("userID");
        String userPassword1 = request.getParameter("userPassword1");
        String userPassword2 = request.getParameter("userPassword2");
        String userName = request.getParameter("userName");
        String userAge = request.getParameter("userAge"); // 아무리 숫자라 하더라도 사용자에게 입력을 받는건 문자열 형태이므로 문자열 선언한다.
        String userGender = request.getParameter("userGender");
        String userEmail = request.getParameter("userEmail");
        String userProfile = request.getParameter("userProfile");


        if (userID == null || userID.equals("") ||
                userPassword1 == null || userPassword1.equals("") ||
                userPassword2 == null || userPassword2.equals("") ||
                userName == null || userName.equals("") ||
                userAge == null || userAge.equals("") ||
                userGender == null || userGender.equals("") ||
                userEmail == null || userEmail.equals("")) { //프로필 사진 업데이트는 사용자 선택
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
            mView.setViewName("join");
            return mView;
        }
        if (!userPassword1.equals(userPassword2)) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "비밀번호가 다릅니다.");
            mView.setViewName("join");
            return mView;
        }
        int result = userService.register(userID, userPassword1, userName, userAge, userGender, userEmail, "");
        if (result == 1) {
            request.getSession().setAttribute("userID", userID);
            request.getSession().setAttribute("messageType", "성공메시지");
            request.getSession().setAttribute("messageContent", "회원가입에 성공하였습니다.");
            mView.setViewName("index");
            return mView;
        } else {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다..");
            mView.setViewName("join");
            return mView;
        }
    }

    //userUpdate

    @RequestMapping(value = "/user/update", method = RequestMethod.POST) //@Responsebody @RestController
    public void userUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userID = request.getParameter("userID");
        HttpSession session = request.getSession(); //세션값 검증
        String userPassword1 = request.getParameter("userPassword1");
        String userPassword2 = request.getParameter("userPassword2");
        String userName = request.getParameter("userName");
        String userAge = request.getParameter("userAge"); // 아무리 숫자라 하더라도 사용자에게 입력을 받는건 문자열 형태이므로 문자열 선언한다.
        String userGender = request.getParameter("userGender");
        String userEmail = request.getParameter("userEmail");

        if (userID == null || userID.equals("") ||
                userPassword1 == null || userPassword1.equals("") ||
                userPassword2 == null || userPassword2.equals("") ||
                userName == null || userName.equals("") ||
                userAge == null || userAge.equals("") ||
                userGender == null || userGender.equals("") ||
                userEmail == null || userEmail.equals("")) { //프로필 사진 업데이트는 사용자 선택
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
            response.sendRedirect("update.jsp");
            return;
        }

        if (!userID.equals((String) session.getAttribute("userID"))) {
            session.setAttribute("messageType", "오류메시지");
            session.setAttribute("messageContent", "접근할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;
        }

        if (!userPassword1.equals(userPassword2)) {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "비밀번호가 다릅니다.");
            response.sendRedirect("update.jsp");
            return;
        }

        int result = userService.update(userID, userPassword1, userName, userAge, userGender, userEmail);
        if (result == 1) {
            request.getSession().setAttribute("userID", userID);
            request.getSession().setAttribute("messageType", "성공메시지");
            request.getSession().setAttribute("messageContent", "회원 정보 수정에 성공하였습니다.");
            response.sendRedirect("index.jsp");
        } else {
            request.getSession().setAttribute("messageType", "오류메시지");
            request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
            response.sendRedirect("update.jsp");
        }
    }
}
