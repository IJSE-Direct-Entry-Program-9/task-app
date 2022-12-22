package lk.ijse.dep9.app.api;

import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/projects/{projectId:\\d+}/tasks")
public class TaskController {

    private final ProjectTaskService taskService;

    public TaskController(ProjectTaskService taskService) {
        this.taskService = taskService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public TaskDTO createNewTask(@RequestBody @Validated(ValidationGroups.Create.class) TaskDTO task, @RequestAttribute String username, @PathVariable int projectId) {
        task.setProjectId(projectId);
        return taskService.createNewTask(username, task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{taskId:\\d+}", consumes = "application/json")
    public void renameTask(@RequestBody @Validated(ValidationGroups.Update.class) TaskDTO task, @RequestAttribute String username, @PathVariable int projectId, @PathVariable int taskId) {
        task.setProjectId(projectId);
        task.setId(taskId);
        taskService.renameTask(username, task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{taskId:\\d+}")
    public void deleteTask(@RequestAttribute String username, @PathVariable int projectId, @PathVariable int taskId) {
        taskService.deleteTask(username, new TaskDTO(taskId, projectId));
    }

    @GetMapping(value = "/{taskId:\\d+}", produces = "application/json")
    public TaskDTO getTaskDetails(@RequestAttribute String username,
                                  @PathVariable int projectId,
                                  @PathVariable int taskId) {
        return taskService.getTaskDetails(username, new TaskDTO(taskId, projectId));
    }

    @GetMapping
    public List<TaskDTO> getAllTasks(@RequestAttribute String username,
                                     @PathVariable int projectId) {
        return taskService.getAllTasks(username, projectId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{taskId:\\d+}", params = "completed")
    public void updateTaskStatus(@RequestAttribute String username,
                                 @PathVariable int projectId,
                                 @PathVariable int taskId,
                                 @RequestParam boolean completed) {
        taskService.updateTaskStatus(username, new TaskDTO(taskId, projectId), completed);
    }
}
