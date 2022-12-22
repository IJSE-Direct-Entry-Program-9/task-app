package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.repository.ProjectRepository;
import lk.ijse.dep9.app.repository.TaskRepository;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.Transformer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Component
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final Transformer transformer;

    public ProjectTaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, Transformer transformer) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.transformer = transformer;
    }

    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return transformer.toProjectDTO(projectRepository.save(transformer.toProject(projectDTO)));
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return projectRepository.findAllProjectsByUsername(username).stream().
                map(transformer::toProjectDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectDetails(String username, int projectId) {
        return projectRepository.findById(projectId).map(transformer::toProjectDTO).get();
    }

    @Override
    public void renameProject(ProjectDTO project) {
        projectRepository.save(transformer.toProject(project));
    }

    @Override
    public void deleteProject(String username, int projectId) {
        taskRepository.findAllTasksByProjectId(projectId).forEach(task -> taskRepository.deleteById(task.getId()));
        projectRepository.deleteById(projectId);
    }

    @Override
    public TaskDTO createNewTask(String username, TaskDTO task) {
        return transformer.toTaskDTO(taskRepository.save(transformer.toTask(task)));
    }

    @Override
    public void renameTask(String username, TaskDTO task) {
        Task taskEntity = taskRepository.findById(task.getId()).get();
        taskEntity.setContent(task.getContent());
        taskRepository.save(transformer.toTask(task));
    }

    @Override
    public void deleteTask(String username, TaskDTO taskDTO) {
        taskRepository.deleteById(taskDTO.getId());
    }

    @Override
    public TaskDTO getTaskDetails(String username, TaskDTO taskDTO) {
        return taskRepository.findById(taskDTO.getId()).map(transformer::toTaskDTO).get();
    }

    @Override
    public List<TaskDTO> getAllTasks(String username, int projectId) {
        return taskRepository.findAllTasksByProjectId(projectId).stream().map(transformer::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTaskStatus(String username, TaskDTO taskDTO, boolean completed) {
        Task task = taskRepository.findById(taskDTO.getId()).get();
        task.setStatus(completed ? Task.Status.COMPLETED : Task.Status.NOT_COMPLETED);
        taskRepository.save(task);
    }
}
