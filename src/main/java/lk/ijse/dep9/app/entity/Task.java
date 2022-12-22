package lk.ijse.dep9.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements SuperEntity {
    private int id;
    private String content;
    private Status status = Status.NOT_COMPLETED;
    private int projectId;

    public Task(String content, Status status, int projectId) {
        this.content = content;
        this.status = status;
        this.projectId = projectId;
    }

    public enum Status {
        COMPLETED, NOT_COMPLETED
    }
}
