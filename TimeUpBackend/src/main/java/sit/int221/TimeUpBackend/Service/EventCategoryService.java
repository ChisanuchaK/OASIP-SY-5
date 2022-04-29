package sit.int221.TimeUpBackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.TimeUpBackend.Entity.EventCategory;
import sit.int221.TimeUpBackend.Repository.EventCategoryRepository;

import java.util.List;

@Service
public class EventCategoryService {
    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    public  List<EventCategory> getAllCategory(){
        return eventCategoryRepository.findAll();
    }
    public EventCategory create(EventCategory newEventCategory){
        return eventCategoryRepository.saveAndFlush(newEventCategory);
    }
}
