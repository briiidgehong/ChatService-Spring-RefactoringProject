package user.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public void FindUser();
    public void LoginUser();
    public void ProfileUser();
    public void RegisterUser();
    public void RegisterCheckUser();
    public void UpdateUser();

}
