package user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dao.UserDAOImpl;
import user.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


@Service
public class UserServiceImpl implements UserService {

    private UserDAOImpl userDAO;

    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int login(String userID, String userPassword) { //로그인
        UserDTO user = userDAO.getUserById(userID);

        if(userDAO.getCheckById(userID) != 0) // 사용자가 존재하면,
        {
            if(userDAO.getUserById(userID).getUserPassword().equals(userPassword))
            {
                return 1; // 로그인 성공
            }
            else
                return  2; // 비밀번호가 틀림
        }
        else
        {
            return 0; // 해당 사용자가 존재하지 않음
        }
    }


    @Override
    public int registerCheck(String userID) { //중복체크

        if (userDAO.getCheckById(userID) != 0) // 사용자가 존재하면,
        {
            return 0; // 이미 존재하는 회원 아이디
        } else
            return 1; // 가입 가능한 회원 아이디
    }

    @Override
    public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile) { //회원가입 수행

        UserDTO user = new UserDTO();
        user.setUserID(userID);
        user.setUserPassword(userPassword);
        user.setUserName(userName);
        user.setUserAge(Integer.parseInt(userAge));
        user.setUserGender(userGender);
        user.setUserEmail(userEmail);
        user.setUserProfile(userProfile);

        if(this.registerCheck(userID) == 1) // 가입 가능한 회원이면,
        {
            userDAO.insertUser(user);
            return 1;
        }
        return -1;
    }

    @Override
    public UserDTO getUser(String userID) {
        UserDTO user = userDAO.getUserById(userID);
        return user;
    }

    @Override
    public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail) { //회원가입 수행
        UserDTO user = new UserDTO();
        user.setUserID(userID);
        user.setUserPassword(userPassword);
        user.setUserName(userName);
        user.setUserAge(Integer.parseInt(userAge));
        user.setUserGender(userGender);
        user.setUserEmail(userEmail);
        user.setUserProfile("0");

        if(this.registerCheck(userID) == 0) // 존재하는 회원이면,
        {
            userDAO.updateUser(user);
            return 1;
        }
        else return -1; // 회원정보 없음
    }

    //프로필 업데이트
    @Override
    public int profile(String userID, String userProfile) {
        HashMap map = new HashMap<String, Object>();
        map.put("userID", userID);
        map.put("userProfile", userProfile);

        int i = userDAO.updateProfile(map);
        return userDAO.updateProfile(map);
    }

    @Override
    public String getProfile(String userID) { //중복체크
        UserDTO user = userDAO.getUserById(userID);
        String userProfile = user.getUserProfile();
        return userProfile;
    }
}
