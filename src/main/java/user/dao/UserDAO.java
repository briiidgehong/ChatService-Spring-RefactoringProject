package user.dao;

import user.dto.UserDTO;

import java.util.HashMap;

public interface UserDAO {
    public UserDTO getUserById(String userID);
    public int getCheckById(String userID);
    public void insertUser(UserDTO user);
    public void updateUser(UserDTO user);
    public int updateProfile(HashMap map);
}
