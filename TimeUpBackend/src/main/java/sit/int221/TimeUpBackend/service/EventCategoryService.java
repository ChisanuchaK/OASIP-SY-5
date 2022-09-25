package sit.int221.TimeUpBackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.EventCategoryDto;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;

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

    public ResponseEntity editEventCategory(EventCategoryDto editEventCategory , Integer id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails.getUsername().equals("admin")){
            EventCategory eventCategory = eventCategoryRepository.findById(id).orElseThrow(
                    ()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "id not found"));

            modelMapper.map(editEventCategory , eventCategory);
            eventCategoryRepository.saveAndFlush(eventCategory);
            return ResponseEntity.status(200).body("Edited Successfully");
        }
        else if (userDetails.getUsername().equals("student")){
            throw  new ResponseStatusException(HttpStatus.BAD_GATEWAY , "this email permission denied");
        }
        throw  new ResponseStatusException(HttpStatus.BAD_GATEWAY , "this email permission denied");
        }

    }

