package sit.int221.TimeUpBackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDto {
    @NotNull

    @Size(min = 1 , max = 100 , message = "size must be between 1 and 100")
    private String nameUser;

    @NotNull
    @Size(min = 1 , max = 50 , message = "size must be between 1 and 50")
    @Email(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}",
            flags =  Pattern.Flag.CASE_INSENSITIVE)
    private String emailUser;


    @NotNull
    @Size(min = 8 , max = 14)
    private String password;

    @Pattern(regexp = "admin|student|lecturer", message = "admin,student,lecturer")
    @Lob
    private String roleUser;
}
