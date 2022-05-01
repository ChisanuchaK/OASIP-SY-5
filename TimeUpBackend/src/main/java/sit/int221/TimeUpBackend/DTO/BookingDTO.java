package sit.int221.TimeUpBackend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.Entity.EventCategory;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer idBooking;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date eventStartTime;
    private Integer eventDuration;
    private EventCategory eventCategoryName;
    private String bookingName;
    }
