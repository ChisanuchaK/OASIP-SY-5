package sit.int221.TimeUpBackend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.Entity.EventCategory;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer idBooking;
    private Instant eventStartTime;
    private Integer eventDuration;
    @JsonIgnore
    private EventCategory eventCategory;
    private String bookingName;
    public String  getEventCategoryName(){
        return eventCategory.getEventCategoryName();
    }
    public Integer getEventCategoryDuration(){return eventCategory.getEventDuration();}
    }


