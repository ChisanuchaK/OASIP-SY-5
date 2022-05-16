package sit.int221.TimeUpBackend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "booking")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbooking" , nullable = false)
    private Integer idBooking;

    @NotEmpty
    @Size(min = 1 , max = 100 , message = "size must be between 1 and 100")
    @Column(name = "bookingName" , length = 100 , nullable = false)
    private String bookingName;

    @Email(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}",
            flags =  Pattern.Flag.CASE_INSENSITIVE)
    @NotNull
//    @Size(min = 1 , max = 100 , message = "size must be between 6 and 100")
    @Column(name = "bookingEmail" , length = 100 , nullable = false)
    private String bookingEmail;


    @ManyToOne
    @JoinColumn(name = "eventCategory"  , nullable = false)
    private EventCategory eventCategory;

    @Future(message = "must be a future date" )
    @NotNull
    @Column(name = "eventStartTime" ,nullable = false )
    private Instant eventStartTime;


    @Column(name = "eventDuration" , nullable = false)
    private Integer eventDuration ;

    @Size(min = 0 , max = 500 , message = "size must be between 0 and 500")
    @Column(name = "eventNotes", length = 500)
    private String eventNotes;



}
