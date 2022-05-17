package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTO.EventCategoryDTO;
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

            modelMapper.map(editEventCategory , eventCategory);
            eventCategoryRepository.saveAndFlush(eventCategory);
            return ResponseEntity.status(200).body("Edited Successfully");
        }

    }

