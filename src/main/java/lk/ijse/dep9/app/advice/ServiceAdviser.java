package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceAdviser {

    private ProjectDAO projectDAO;

    public ServiceAdviser(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Before(value = "execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(..)) &&" +
            " args(username,projectId)", argNames = "username,projectId")
    public void serviceMethodAuthorization(String username, int projectId){
        Project project = projectDAO.findById(projectId).orElseThrow(
                () -> new EmptyResultDataAccessException(1));
        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
    }
}
