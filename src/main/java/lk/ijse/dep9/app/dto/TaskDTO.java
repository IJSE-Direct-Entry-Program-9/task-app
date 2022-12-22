package lk.ijse.dep9.app.dto;

import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Serializable {
    @Null(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "Task id can't be specified")
    private Integer id;
    @NotBlank(message = "Task content can't be empty or null")
    private String content;
    @Null(message = "Task id can't be specified")
    private Integer projectId;
    @Null(message = "Task status is ready-only")
    private boolean isCompleted;

    public TaskDTO(Integer id, Integer projectId) {
        this.id = id;
        this.projectId = projectId;
    }
}
