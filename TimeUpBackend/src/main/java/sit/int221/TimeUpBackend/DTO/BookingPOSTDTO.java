package sit.int221.TimeUpBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.Entity.EventCategory;

import javax.validation.constraints.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingPOSTDTO {
    @Future(message = "must be a future date" )
    @NotNull
    private Instant eventStartTime;
    @Size(min = 0 , max = 500 , message = "size must be between 0 and 500")
    private String eventNotes;
    @NotNull
    private EventCategory eventCategory;
    @Email(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}",
            flags =  Pattern.Flag.CASE_INSENSITIVE)
    @NotNull
    private String bookingEmail;
    @NotEmpty
    @Size(min = 1 , max = 100 , message = "size must be between 1 and 100")
    private String bookingName;
}