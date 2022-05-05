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
public class BookingDTO {
    private Integer idBooking;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date eventStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date eventEndTime;
    private Integer eventDuration;
    @JsonIgnore
    private EventCategory eventCategory;
    private String bookingName;
    public String  getEventCategoryName(){
        return eventCategory.getEventCategoryName();
    }
    public Integer getEventCategoryDuration(){return eventCategory.getEventDuration();}
    }


