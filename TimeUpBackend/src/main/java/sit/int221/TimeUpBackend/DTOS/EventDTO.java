package sit.int221.TimeUpBackend.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.Entities.EventCategory;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
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
    public Integer getEventCategoryId(){return  eventCategory.getEventCategoryId();}
    public Instant getEventEndTime(){return  eventStartTime.plusMillis(eventDuration * 60000);}
    }


