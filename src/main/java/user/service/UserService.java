package user.service;

import org.springframework.stereotype.Service;
import user.dto.UserDTO;

@Service
public interface UserService {
    public int login(String userID, String userPassword);
    public int registerCheck(String userID);
    public int register(String userID,
                        String userPassword,
                        String userName,
                        String userAge,
                        String userGender,
                        String userEmail,
                        String userProfile);
    public UserDTO getUser(String userID);
    public int update(String userID,
                      String userPassword,
                      String userName,
                      String userAge,
                      String userGender,
                      String userEmail);
    public int profile(String userID, String userProfile);
    public String getProfile(String userID);
}
