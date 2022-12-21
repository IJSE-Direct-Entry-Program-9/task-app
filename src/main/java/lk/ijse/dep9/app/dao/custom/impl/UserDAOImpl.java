package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbc;

    public UserDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User save(User user) {
        jdbc.update("INSERT INTO User (username, full_name, password) VALUES (?, ?, ?)",
                user.getUsername(), user.getFullName(), user.getPassword());
        return user;
    }

    @Override
    public void update(User user) {
        jdbc.update("UPDATE User SET full_name=?, password=? WHERE username=?",
                user.getFullName(), user.getPassword(), user.getUsername());
    }

    @Override
    public void deleteById(String username) {
        jdbc.update("DELETE FROM User WHERE username=?", username);
    }

    @Override
    public Optional<User> findById(String username) {
        return Optional.ofNullable(jdbc.query("SELECT full_name, password FROM User WHERE username=?", rst -> {
            return new User(username,
                    rst.getString("password"),
                    rst.getString("full_name"));
        }, username));
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM User", (rst, rowNum) ->
                new User(rst.getString("username"),
                        rst.getString("password"),
                        rst.getString("full_name")));
    }

    @Override
    public long count() {
        return jdbc.queryForObject("SELECT COUNT(username) FROM User", Long.class);
    }

    @Override
    public boolean existsById(String username) {
        return findById(username).isPresent();
    }
}
