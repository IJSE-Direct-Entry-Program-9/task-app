package lk.ijse.dep9.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "taskSet")
@EqualsAndHashCode(exclude = "taskSet")
public class Project implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User user;
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @Setter(AccessLevel.NONE)
    private Set<Task> taskSet = new HashSet<>();

    public Project(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
