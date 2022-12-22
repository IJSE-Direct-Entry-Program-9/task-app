package lk.ijse.dep9.app.repository;

import lk.ijse.dep9.app.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    @Query(value = "select p from Project p where p.user.username = ?1")
    List<Project> findAllProjectsByUsername(String username);
}
