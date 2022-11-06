package sit.int221.TimeUpBackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "eventCategoryOwner")
public class EventCategoryOwner {
    @EmbeddedId
    private EventCategoryOwnerId eventCategoryOwnerId;

    @MapsId("eventcategoryEventcategoryid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eventCategory_eventCategoryId", nullable = false)
    private EventCategory eventcategoryEventcategory;

    @MapsId("userIduser")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_iduser", nullable = false)
    private User userIduser;


}
