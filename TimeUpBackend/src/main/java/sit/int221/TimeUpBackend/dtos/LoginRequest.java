package sit.int221.TimeUpBackend.dtos;

import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import javax.validation.constraints.*;

@Data
public class LoginRequest {
    @NotNull
    @Email(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}",
            flags =  Pattern.Flag.CASE_INSENSITIVE)
    private String emailUser;

    @NotNull
    @Size(min = 8 , max = 14)
    private String password;
    }

