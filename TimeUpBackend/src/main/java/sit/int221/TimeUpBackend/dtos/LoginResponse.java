package sit.int221.TimeUpBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginResponse {
    private SuccessFailure status;
    private String message;
    @NotNull
    private int userId;
    @NotNull
    private String userName;
    @NotNull
    private String userEmail;
    @NotNull
    private String userRole;
    public enum SuccessFailure {
        SUCCESS, FAILURE
    }
}
