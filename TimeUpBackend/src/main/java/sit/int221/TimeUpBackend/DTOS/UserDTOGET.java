package sit.int221.TimeUpBackend.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.Entities.RoleUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOGET {
    @NotNull
    @Size(min = 1 , max = 100 , message = "size must be between 1 and 100")
    private String nameUser;
    @NotNull
    @Size(min = 1 , max = 50 , message = "size must be between 1 and 50")
    private String emailUser;
    @NotNull(message = "role null")

    private RoleUser roleUser;
    private Instant createOn;
    private Instant updateOn;
}
