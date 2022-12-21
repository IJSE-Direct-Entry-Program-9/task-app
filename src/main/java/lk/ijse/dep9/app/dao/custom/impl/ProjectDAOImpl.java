package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.entity.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectDAOImpl implements ProjectDAO {

    private final JdbcTemplate jdbc;

    public ProjectDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Project save(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement stm = con.prepareStatement("INSERT INTO Project (name, username) VALUES (?, ?)");
            stm.setString(1, project.getName());
            stm.setString(2, project.getUsername());
            return stm;
        }, keyHolder);
        project.setId(keyHolder.getKey().intValue());
        return project;
    }

    @Override
    public void update(Project project) {
        jdbc.update("UPDATE Project SET name=? AND username =? WHERE id=?",
                project.getName(), project.getUsername(), project.getId());
    }

    @Override
    public void deleteById(Integer id) {
        jdbc.update("DELETE FROM Project WHERE id=?", id);
    }

    @Override
    public Optional<Project> findById(Integer id) {
        return Optional.ofNullable(jdbc.query("SELECT * FROM Project WHERE id=?", rst -> {
            return new Project(rst.getInt("id"),
                    rst.getString("name"),
                    rst.getString("username"));
        }, id));
    }

    @Override
    public List<Project> findAll() {
        return jdbc.query("SELECT * FROM Project", (rst, rowIndex) ->
                new Project(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("username")));
    }

    @Override
    public long count() {
        return jdbc.queryForObject("SELECT COUNT(id) FROM Project", Long.class);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Project> findAllProjectsByUsername(String username) {
        return jdbc.query("SELECT * FROM Project WHERE username = ?", (rst, rowIndex) ->
                new Project(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("username")), username);
    }
}
