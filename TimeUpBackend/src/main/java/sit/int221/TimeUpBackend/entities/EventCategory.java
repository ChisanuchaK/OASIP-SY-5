package sit.int221.TimeUpBackend.entities;

import lombok.Data;
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
    @Column(name = "eventCategoryId", nullable = false)
    private Integer eventCategoryId;

    @Column(name = "eventCategoryName", nullable = false, length = 100)
    private String eventCategoryName;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    @Column(name = "eventColor", nullable = false, length = 45)
    private String eventColor;

    @Column(name = "eventCategoryDescription", length = 500)
    private String eventCategoryDescription;

}