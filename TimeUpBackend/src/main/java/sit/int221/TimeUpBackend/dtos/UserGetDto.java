package sit.int221.TimeUpBackend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.entities.User;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {
    private Integer idUser;
    @NotNull
    @Size(min = 1 , max = 100 , message = "size must be between 1 and 100")
    private String nameUser;
    @NotNull
    @Size(min = 1 , max = 50 , message = "size must be between 1 and 50")
    private String emailUser;
    @NotNull(message = "role null")
    @Pattern(regexp = "admin|student|lecturer", message = "admin,student,lecturer")
    @Lob
    private String roleUser;
    private Instant createOn;
    private Instant updateOn;

}
