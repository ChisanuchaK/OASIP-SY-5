package sit.int221.TimeUpBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.entities.EventCategoryOwner;
import sit.int221.TimeUpBackend.entities.EventCategoryOwnerId;
import sit.int221.TimeUpBackend.entities.User;

import java.util.List;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, EventCategoryOwnerId> {

    EventCategoryOwner findByUserIduser(User user);
    List<EventCategoryOwner> findAllByUserIduser(User user);

}