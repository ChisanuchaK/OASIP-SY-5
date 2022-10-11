package sit.int221.TimeUpBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.entities.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {


}
