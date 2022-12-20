package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDAOImpl implements ProjectDAO {

    private final Connection connection;

    public ProjectDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Project save(Project project) {
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Project (name, username) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, project.getName());
            stm.setString(2, project.getUsername());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            project.setId(generatedKeys.getInt(1));
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Project project) {
        try {
            PreparedStatement stm = connection.
                    prepareStatement("UPDATE Project SET name=? AND username =? WHERE id=?");
            stm.setString(1, project.getName());
            stm.setString(2, project.getUsername());
            stm.setInt(3, project.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Project WHERE id=?");
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> findById(Integer id) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Project WHERE id=?");
            stm.setInt(1, id);
            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                return Optional.of(new Project(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("username")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        try {
            List<Project> projectList = new ArrayList<>();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Project");
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                projectList.add(new Project(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("username")));
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(id) FROM Project");
            ResultSet rst = stm.executeQuery();
            rst.next();
            return rst.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Project> findAllProjectsByUsername(String username) {
        try {
            List<Project> projectList = new ArrayList<>();
            PreparedStatement stm = connection.
                    prepareStatement("SELECT * FROM Project WHERE username = ?");
            stm.setString(1, username);
            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                projectList.add(new Project(rst.getInt("id"),
                        rst.getString("name"),
                        rst.getString("username")));
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
