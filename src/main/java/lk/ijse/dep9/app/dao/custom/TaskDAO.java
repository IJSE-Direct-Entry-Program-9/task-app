package lk.ijse.dep9.app.dao.custom;

import lk.ijse.dep9.app.dao.CrudDAO;
import lk.ijse.dep9.app.entity.Task;

import java.util.List;

public interface TaskDAO extends CrudDAO<Task, Integer> {

    List<Task> findAllTasksByProjectId(Integer projectId);
}
