package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.exception.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final Transformer transformer;

    public UserServiceImpl(UserDAO userDAO, Transformer transformer) {
        this.userDAO = userDAO;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.save(transformer.toUser(userDTO));
    }

    @Override
    public UserDTO verifyUser(String username, String password) {
        UserDTO user = userDAO.findById(username).map(transformer::toUserDTO)
                .orElseThrow(AuthenticationException::new);
        if (DigestUtils.sha256Hex(password).equals(user.getPassword())){
            return user;
        }
        throw new AuthenticationException();
    }
}
