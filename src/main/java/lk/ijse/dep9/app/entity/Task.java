package lk.ijse.dep9.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Project project;

    public Task(String content, Status status, Project project) {
        this.content = content;
        this.status = status;
        this.project = project;
    }

    public enum Status {
        COMPLETED, NOT_COMPLETED
    }
}
