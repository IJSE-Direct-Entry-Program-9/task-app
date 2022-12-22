package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@Slf4j
public class ServiceAdviser {

    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    public ServiceAdviser(ProjectDAO projectDAO, TaskDAO taskDAO) {
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
    }

    @Pointcut("execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(..))")
    public void serviceMethodAuthorization() {
    }

    @Before(value = "serviceMethodAuthorization() && args(username,projectId)",
            argNames = "username,projectId")
    public void serviceMethodAuthorization(String username, int projectId) {
        executeAdvice(username, projectId);
    }

    @Before(value = "serviceMethodAuthorization() && args(project)", argNames = "project")
    public void serviceMethodAuthorization(ProjectDTO project) {
        if (project.getId() != null) executeAdvice(project.getUsername(), project.getId());
    }

    @Before(value = "serviceMethodAuthorization() && args(username, task, ..)", argNames = "username,task")
    public void serviceMethodAuthorization(String username, TaskDTO task) {
        executeAdvice(username, task.getProjectId());
        if (task.getId() != null) {
            taskDAO.findById(task.getId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
            if (taskDAO.findAllTasksByProjectId(task.getProjectId())
                    .stream().noneMatch(t -> t.getId() == task.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    private void executeAdvice(String username, int projectId) {
        Project project = projectDAO.findById(projectId).orElseThrow(
                () -> new EmptyResultDataAccessException(1));
        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
    }
}
