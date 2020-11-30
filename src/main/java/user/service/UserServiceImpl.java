package user.service;


import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dao.UserDAO;
import user.dto.UserDTO;


@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void FindUser() {
        System.out.println("finduser 들어옴");
        UserDTO user2 = userDAO.getUser2();
        System.out.println(user2.getUserEmail());
    }

    public void LoginUser() {

    }

    public void ProfileUser() {

    }

    public void RegisterUser() {

    }

    public void RegisterCheckUser() {

    }

    public void UpdateUser() {

    }
}
