package sit.int221.TimeUpBackend.DTOS;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.Entities.RoleUser;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOPOST {
    private String nameUser;
    private String emailUser;
    private RoleUser roleUser;
}
