package lk.ijse.dep9.app.repository;

import lk.ijse.dep9.app.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("select t from Task t where t.project.id = ?1")
    List<Task> findAllTasksByProjectId(Integer projectId);
}
