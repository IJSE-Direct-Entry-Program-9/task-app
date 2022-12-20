package lk.ijse.dep9.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$")
    private String fullName;
    @NotBlank
    private String username;
    @NotEmpty
    @Length(min = 3)
    private String password;
}
