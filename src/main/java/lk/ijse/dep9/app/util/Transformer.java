package lk.ijse.dep9.app.util;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

    @Autowired
    private ModelMapper mapper;

    public User toUser(UserDTO dto){
        return mapper.map(dto, User.class);
    }
}
