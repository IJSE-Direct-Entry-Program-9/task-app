package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.DAOFactory;
import lk.ijse.dep9.app.dao.DAOTypes;
import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dao.util.ConnectionUtil;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.User;
import lk.ijse.dep9.app.service.custom.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Scope("request")
public class UserServiceImpl implements UserService {

    public UserServiceImpl() {

    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        Connection connection = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/task-app");
            connection = dataSource.getConnection();
            ConnectionUtil.setConnection(connection);
            UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOTypes.USER, UserDAO.class);
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }finally{
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
