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
import sit.int221.TimeUpBackend.dtos.EventDto;
import sit.int221.TimeUpBackend.entities.Event;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.EventCategoryOwner;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.EventCategoryOwnerRepository;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser"){
            return eventCategoryRepository.findAll();
        }
        else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmailUser(userDetails.getUsername());

        if(user.getRoleUser().equals("admin") || user.getRoleUser().equals("student")){
            return eventCategoryRepository.findAll();
        }
        else if (user.getRoleUser().equals("lecturer")) {
            ArrayList<EventCategory> eventArrayList = new ArrayList<>();
            List<EventCategoryOwner> eventCategoryOwner = eventCategoryOwnerRepository.findAllByUserIduser(user);
            for(int i = 0 ; i < eventCategoryOwner.size() ; i ++){
               eventArrayList.add(eventCategoryRepository.findByEventCategoryId(eventCategoryOwner.get(i).getEventcategoryEventcategory().getEventCategoryId()));
            }
            return eventArrayList.stream().map(e -> modelMapper.map(e, EventCategory.class)).collect(Collectors.toList());
        }
        }
        throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public EventCategory create(EventCategory newEventCategory){
        return eventCategoryRepository.saveAndFlush(newEventCategory);
    }
    public ResponseEntity addCategoryOwner(CategoryOwnerDto categoryOwnerDto){
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

    public ResponseEntity deleteCategoryOwner(Integer id){
        eventCategoryOwnerRepository.deleteById(id);
        return  ResponseEntity.ok().body("delete category owner id : " + id +" " +  "success");
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
        else if (user.getRoleUser().equals("lecturer")){
            List<EventCategoryOwner> eventCategoryOwner = eventCategoryOwnerRepository.findAllByUserIduser(user);
            for (int i = 0 ; i < eventCategoryOwner.size() ; i++){
                if(id == eventCategoryOwner.get(i).getEventcategoryEventcategory().getEventCategoryId()){
                    EventCategory eventCategory = eventCategoryRepository.findById(id).orElseThrow(
                            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "id not found"));

                    modelMapper.map(editEventCategory , eventCategory);
                    eventCategoryRepository.saveAndFlush(eventCategory);
                    return ResponseEntity.status(200).body("Edited Successfully");
                }
            }
        }
        throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "this email permission denied");
        }

    }

