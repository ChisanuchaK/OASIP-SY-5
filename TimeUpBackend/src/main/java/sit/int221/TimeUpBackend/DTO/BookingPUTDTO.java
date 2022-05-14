package sit.int221.TimeUpBackend.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingPUTDTO {

//    private Integer idBooking;
    private Instant eventStartTime;
    private String eventNotes;
}
