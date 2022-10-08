package sit.int221.TimeUpBackend.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EventCategoryOwnerId implements Serializable {
    private static final long serialVersionUID = 5108133252778243326L;
    @Column(name = "eventCategory_eventCategoryId", nullable = false)
    private Integer eventcategoryEventcategoryid;
    @Column(name = "user_iduser", nullable = false)
    private Integer userIduser;

    @Override
    public int hashCode() {
        return Objects.hash(userIduser, eventcategoryEventcategoryid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventCategoryOwnerId entity = (EventCategoryOwnerId) o;
        return Objects.equals(this.userIduser, entity.userIduser) &&
                Objects.equals(this.eventcategoryEventcategoryid, entity.eventcategoryEventcategoryid);
    }
}