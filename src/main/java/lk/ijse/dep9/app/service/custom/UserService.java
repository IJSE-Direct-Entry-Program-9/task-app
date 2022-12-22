package lk.ijse.dep9.app.service.custom;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.service.SuperService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends SuperService, UserDetailsService {

    void createNewUserAccount(UserDTO userDTO);

    UserDTO verifyUser(String username, String password);

    UserDTO getUserAccountDetails(String username);

    void updateUserAccountDetails(UserDTO userDTO);

    void deleteUserAccount(String username);

}
