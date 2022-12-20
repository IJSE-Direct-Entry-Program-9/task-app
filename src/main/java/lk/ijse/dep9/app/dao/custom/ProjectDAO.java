package lk.ijse.dep9.app.dao.custom;

import lk.ijse.dep9.app.dao.CrudDAO;
import lk.ijse.dep9.app.entity.Project;

import java.util.List;

public interface ProjectDAO extends CrudDAO<Project, Integer> {

    List<Project> findAllProjectsByUsername(String username);
}
