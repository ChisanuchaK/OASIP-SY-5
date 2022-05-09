package sit.int221.TimeUpBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.Entity.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory , Integer> {
}
