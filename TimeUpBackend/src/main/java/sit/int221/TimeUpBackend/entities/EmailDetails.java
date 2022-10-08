package sit.int221.TimeUpBackend.entities;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}