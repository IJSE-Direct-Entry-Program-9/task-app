package lk.ijse.dep9.app.service.custom;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.service.SuperService;

public interface UserService extends SuperService {

    void createNewUserAccount(UserDTO userDTO);

}
