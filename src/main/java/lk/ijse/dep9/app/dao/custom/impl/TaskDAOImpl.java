package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.entity.Task;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TaskDAOImpl implements TaskDAO {

    private final Connection connection;

    public TaskDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Task save(Task task) {
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Task (content, status, project_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, task.getContent());
            stm.setString(2, task.getStatus().toString());
            stm.setInt(3, task.getProjectId());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            task.setId(generatedKeys.getInt(1));
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Task task) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE Task SET content=?, status=?, project_id=? WHERE id=?");
            stm.setString(1, task.getContent());
            stm.setString(2, task.getStatus().toString());
            stm.setInt(3, task.getProjectId());
            stm.setInt(4, task.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Task WHERE id=?");
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Task> findById(Integer id) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Task WHERE id=?");
            stm.setInt(1, id);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return Optional.of(new Task(rst.getInt("id"),
                        rst.getString("content"),
                        Task.Status.valueOf(rst.getString("status")),
                        rst.getInt("project_id")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            List<Task> taskList = new ArrayList<>();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Task");
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                taskList.add(new Task(rst.getInt("id"),
                        rst.getString("content"),
                        Task.Status.valueOf(rst.getString("status")),
                        rst.getInt("project_id")));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(id) FROM Task");
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
    public List<Task> findAllTasksByProjectId(Integer projectId) {
        try {
            List<Task> taskList = new ArrayList<>();
            PreparedStatement stm = connection.
                    prepareStatement("SELECT * FROM Task WHERE project_id = ?");
            stm.setInt(1, projectId);
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                taskList.add(new Task(rst.getInt("id"),
                        rst.getString("content"),
                        Task.Status.valueOf(rst.getString("status")),
                        rst.getInt("project_id")));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
