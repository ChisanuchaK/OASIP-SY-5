package sit.int221.TimeUpBackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryDto {

    @NotNull
    @Size(min = 1 , max = 500 , message = "size must be between 1 and 100")
    private String eventCategoryName;

    @NotNull
    @Min(1)
    @Max(480)
    private Integer eventDuration;

    @Size(min = 0 , max = 500 , message = "size must be between 0 and 500")
    private String eventCategoryDescription;
}
