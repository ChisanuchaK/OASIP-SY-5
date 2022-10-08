package sit.int221.TimeUpBackend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.User;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventMoreDetailDto {
    private Integer idBooking;
    private Instant eventStartTime;
    private Integer eventDuration;
    @JsonIgnore
    private EventCategory eventCategory;
    private String eventCategoryName;
    private String bookingName;
    private String bookingEmail;
    private String eventNotes;
    @JsonIgnore
    private User user;
    public String  getEventCategoryName(){
        return eventCategory.getEventCategoryName();
    }
    public Integer getEventCategoryId(){return  eventCategory.getEventCategoryId();}
    public Integer getEventCategoryDuration(){return eventCategory.getEventDuration();}
}
