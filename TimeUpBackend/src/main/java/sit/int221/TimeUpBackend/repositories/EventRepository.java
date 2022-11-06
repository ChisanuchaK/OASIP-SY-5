package sit.int221.TimeUpBackend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.entities.Event;
import sit.int221.TimeUpBackend.entities.EventCategoryOwner;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
   List<Event> findAllByEventCategoryEventCategoryId(Integer eventCategoryId);
   List<Event> findAllByBookingEmail(String bookingEmail);
   Event findByBookingEmail(String bookingEmail);
   Event findByFileName(String fileName);




}
