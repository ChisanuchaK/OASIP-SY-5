package sit.int221.TimeUpBackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPutDto {

    @Future(message = "must be a future date and not present time !!" )
    @NotNull
    private Instant eventStartTime;

    @Size(min = 0 , max = 500 , message = "size must be between 0 and 500")
    private String eventNotes;


}
