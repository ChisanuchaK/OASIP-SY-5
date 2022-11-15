package sit.int221.TimeUpBackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.CategoryOwnerDto;
import sit.int221.TimeUpBackend.dtos.EventCategoryDto;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.EventCategoryOwner;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.EventCategoryOwnerRepository;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.util.List;

@Service
public class EventCategoryService {
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventCategoryOwnerRepository eventCategoryOwnerRepository;

    public  List<EventCategory> getAllCategory(){
        return eventCategoryRepository.findAll();
    }

    public EventCategory create(EventCategory newEventCategory){
        return eventCategoryRepository.saveAndFlush(newEventCategory);
    }
    public ResponseEntity addEventCategoryOwner(CategoryOwnerDto categoryOwnerDto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(userDetails.getUsername());
        if(user.getRoleUser().equals("admin")){
            eventCategoryOwnerRepository.saveAndFlush(modelMapper.map(categoryOwnerDto , EventCategoryOwner.class));
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN , "permission denied");
        }
        return ResponseEntity.status(201).body("crate event category owner successfully");
    }


    public ResponseEntity editEventCategory(EventCategoryDto editEventCategory , Integer id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(userDetails.getUsername());
        if(user.getRoleUser().equals("admin")){
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

