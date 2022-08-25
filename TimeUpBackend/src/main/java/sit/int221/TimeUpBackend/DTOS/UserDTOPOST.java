package sit.int221.TimeUpBackend.DTOS;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import sit.int221.TimeUpBackend.Entities.RoleUser;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOPOST {
    @NotNull
    @Size(min = 1 , max = 100 , message = "size must be between 1 and 100")
    private String nameUser;

    @NotNull
    @Size(min = 1 , max = 50 , message = "size must be between 1 and 50")
    @Email(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}",
            flags =  Pattern.Flag.CASE_INSENSITIVE)
    private String emailUser;

    @Size(min = 8 , max = 14)
    private String password;
    private RoleUser roleUser;
}
