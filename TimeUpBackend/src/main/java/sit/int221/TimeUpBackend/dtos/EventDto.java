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
public class EventDto {
    private Integer idBooking;
    private Instant eventStartTime;
    private Integer eventDuration;
    @JsonIgnore
    private EventCategory eventCategory;
    @JsonIgnore
    private User user;
    private String bookingName;

    private String bookingEmail;
    public String getBookingEmail(){
        if(user == null){
            return  bookingEmail;
        }
        else {
            return user.getEmailUser();
        }
    }

    public int getUserId(){
        return user.getIdUser();
    }
    public String  getEventCategoryName(){
        return eventCategory.getEventCategoryName();
    }
    public Integer getEventCategoryDuration(){return eventCategory.getEventDuration();}
    public Integer getEventCategoryId(){return  eventCategory.getEventCategoryId();}
    public Instant getEventEndTime(){return  eventStartTime.plusMillis(eventDuration * 60000);}
    }


