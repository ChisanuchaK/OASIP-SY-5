package sit.int221.TimeUpBackend.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingPUTDTO {

//    private Integer idBooking;
    @FutureOrPresent(message = "must be a future date" )
    private Instant eventStartTime;
    @Size(min = 0 , max = 500 , message = "size must be between 0 and 500")
    private String eventNotes;
}
