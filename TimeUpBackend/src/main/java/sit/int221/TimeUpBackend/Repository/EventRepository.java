package sit.int221.TimeUpBackend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.Entities.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
   List<Event> findAllByEventCategoryEventCategoryId(Integer eventCategoryId);

}
