package sit.int221.TimeUpBackend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "booking")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbooking" , nullable = false)
    private Integer idBooking;

    @Column(name = "bookingName" , length = 100 , nullable = false)
    private String bookingName;

    @Column(name = "bookingEmail" , length = 100 , nullable = false)
    private String bookingEmail;

    @ManyToOne
    @JoinColumn(name = "eventCategory"  , nullable = false)
    private EventCategory eventCategory;


    @Column(name = "eventStartTime" ,nullable = false )
    private Instant eventStartTime;

    @Column(name = "eventDuration" , nullable = false)
    private Integer eventDuration ;

    @Column(name = "eventNotes", length = 500)
    private String eventNotes;



}
