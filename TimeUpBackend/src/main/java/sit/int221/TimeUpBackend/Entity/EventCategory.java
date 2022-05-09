package sit.int221.TimeUpBackend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "eventCategory")
public class EventCategory {
    @Id
    @Column(name = "eventId" , nullable = false)
    private  Integer eventId;
    @Column(name = "eventCategoryName", length = 100, unique = true, nullable = false)
    private String eventCategoryName;
    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;
    @Column(name = "eventColor" , nullable = false)
    private String eventColor;
    @Column(name = "eventCategoryDescription", length = 500)
    private String eventCategoryDescription;




}