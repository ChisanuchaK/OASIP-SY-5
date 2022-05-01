package sit.int221.TimeUpBackend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BookingMoreDetailDTO {
    private Integer idBooking;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date eventStartTime;
    private Integer eventDuration;
    @JsonIgnore
    private EventCategory eventCategory;
    private String eventCategoryName;
    private String bookingName;
    private String bookingEmail;
    private String eventNotes;
    public String  getEventCategoryName(){
        return eventCategory.getEventCategoryName();
    }
}
