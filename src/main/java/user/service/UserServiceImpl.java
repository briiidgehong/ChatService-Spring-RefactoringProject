package user.service;

import lombok.Getter;
import lombok.Setter;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dao.UserDAO;

@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void FindUser() {

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
