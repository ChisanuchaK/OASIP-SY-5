package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTO.EventCategoryDTO;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Entity.EventCategory;
import sit.int221.TimeUpBackend.Repository.EventCategoryRepository;

import java.util.List;

@Service
public class EventCategoryService {
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public  List<EventCategory> getAllCategory(){
        return eventCategoryRepository.findAll();
    }
    public EventCategory create(EventCategory newEventCategory){
        return eventCategoryRepository.saveAndFlush(newEventCategory);
    }

    public ResponseEntity editEventCategory(EventCategoryDTO editEventCategory , Integer id){
        EventCategory eventCategory = eventCategoryRepository.findById(id).orElseThrow( ()->{
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        if ((eventCategory.getEventCategoryName().length() > 0 && eventCategory.getEventCategoryName().length() <=100)
          && (eventCategory.getEventCategoryDescription().length() > 0 && eventCategory.getEventCategoryDescription().length() <=500)
         && (eventCategory.getEventDuration() >= 1 && eventCategory.getEventDuration() <= 480 )){
            modelMapper.map(editEventCategory , eventCategory);
            eventCategoryRepository.saveAndFlush(eventCategory);
            return ResponseEntity.status(200).body("Edited Successfully");
        }
        else {
            return ResponseEntity.status(400).body("Can't Edit !!");
        }
    }
}
