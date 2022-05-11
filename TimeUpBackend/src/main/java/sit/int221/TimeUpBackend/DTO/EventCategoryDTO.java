package sit.int221.TimeUpBackend.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryDTO {
    private  Integer eventCategoryId;
    private String eventCategoryName;
    private Integer eventDuration;
    private String eventCategoryDescription;
}
