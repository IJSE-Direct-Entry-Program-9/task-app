package lk.ijse.dep9.app.dto;

import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO implements Serializable {
    @Null(groups = ValidationGroups.Create.class, message = "Project id can't be specified")
    private Integer id;
    @NotBlank(message = "Project name can't be null or empty")
    @Length(min = 3, message = "Project name should be at least 3 characters long")
    private String name;
    private String username;
}
