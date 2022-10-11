package sit.int221.TimeUpBackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.entities.Event;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.EventRepository;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService{

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmailServiceImpl emailService;

    private ModelMapper modelMapper = new ModelMapper();
    //    get
    public List<EventDto> getAllEvent(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        if(user.getRoleUser().equals("admin")){
            List<Event>bookings= eventRepository.findAll();
            return bookings.stream().map(e -> modelMapper.map(e, EventDto.class)).collect(Collectors.toList());
        }
        else if (user.getRoleUser().equals("student")){
                List<Event> bookingsFromRoleUser = eventRepository.findAllByBookingEmail(getCurrentAuthentication.getUsername());
                return bookingsFromRoleUser.stream().map(e -> modelMapper.map(e, EventDto.class)).collect(Collectors.toList());
        }
      throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "That the booking email must be the same as student's email");
    }

    public EventMoreDetailDto getEventDetailDTOById(Integer id){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        Event event = eventRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "id event not found"));
        if(user.getRoleUser().equals("admin")){
            Event bookings= eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return modelMapper.map(bookings , EventMoreDetailDto.class);
        }
        else if (user.getRoleUser().equals("student") ){
            if(!(event.getBookingEmail().equals(user.getEmailUser()))){
                throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "email is not the same as student's email");
            }
            else{
                Event bookings= eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                return modelMapper.map(bookings , EventMoreDetailDto.class);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "This email permission denied");
    }
    public List<EventDto> getEventByIdCategory(@PathVariable Integer id){
            EventCategory eventCategory = eventCategoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            List<Event> bookingList = eventRepository.findAllByEventCategoryEventCategoryId(eventCategory.getEventCategoryId());
        return bookingList.stream().map(e -> modelMapper.map(e, EventDto.class)).collect(Collectors.toList());
    }
    // post

    public ResponseEntity create( EventPostDto eventPostDto) {
        User checkUserByEmail = userRepository.findByEmailUser(eventPostDto.getBookingEmail());
        Event booking = modelMapper.map(eventPostDto, Event.class);
        EventCategory eventCategory = eventCategoryRepository.findById(eventPostDto.getEventCategory().getEventCategoryId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Event> checkCompare = eventRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser"){
            if (checkUserByEmail == null) {
                booking.setEventDuration(eventCategory.getEventDuration());
                booking.setBookingEmail(eventPostDto.getBookingEmail());
                if (!checkTimeOverLap(checkCompare, booking)) {
                    eventRepository.save(booking);
                    emailService.sendMailWithAttachment(eventPostDto);
                    return ResponseEntity.status(201).body("Inserted Successfully");
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
                }
            }
        }
        else {
            UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
            if (user.getRoleUser().equals("admin")) {
                if (checkUserByEmail != null) {
                    booking.setEventDuration(eventCategory.getEventDuration());
                    booking.setBookingEmail(eventPostDto.getBookingEmail());
                    booking.setUserIduser(checkUserByEmail);

                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email not register");
                }
                if (!checkTimeOverLap(checkCompare, booking)) {
                    eventRepository.save(booking);
                    emailService.sendMailWithAttachment(eventPostDto);
                    return ResponseEntity.status(201).body("Inserted Successfully");
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
                }
            } else if (user.getRoleUser().equals("student") && user.getEmailUser().equals(checkUserByEmail.getEmailUser())) {
                booking.setEventDuration(eventCategory.getEventDuration());
                booking.setBookingEmail(checkUserByEmail.getEmailUser());
                booking.setUserIduser(checkUserByEmail);
                if (!checkTimeOverLap(checkCompare, booking)) {
                    eventRepository.save(booking);
                    emailService.sendMailWithAttachment(eventPostDto);
                    return ResponseEntity.status(201).body("Inserted Successfully");
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "booking email must be the same as the student's email");
    }
    public boolean checkTimeOverLap(List<Event> allEvent, Event event ) {

        for (Event book : allEvent) {
            if (((event.getEventStartTime().toEpochMilli() >= book.getEventStartTime().toEpochMilli())
                    && ((event.getEventStartTime().toEpochMilli() <= endTimeMs(book))))
                    || (( (endTimeMs(event)) >= book.getEventStartTime().toEpochMilli())
                    && ((endTimeMs(event))<= endTimeMs(book)))
                    || ((event.getEventStartTime().toEpochMilli() <= book.getEventStartTime().toEpochMilli())
                    && (endTimeMs(event) >= endTimeMs(book)))) {
                return true;
            }
        }
        return false;
    }

    public long endTimeMs(Event time){
        return (time.getEventStartTime().toEpochMilli() + ((time.getEventDuration() * 60000)));
    }
    // delete
    public void delete ( Integer id){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        Event event = eventRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Event not found"));

        if(user.getRoleUser().equals("admin")){
            eventRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.OK , "Delete" + " " + id +  " " + user.getEmailUser() + " "  +"successful");
        }
        else if (user.getRoleUser().equals("student")){
            if(!(event.getBookingEmail().equals(user.getEmailUser()))){
                throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "email is not the same as student's email");
            }
            else{
                eventRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.OK , "Delete" + " " + id +  " " + user.getEmailUser() + " "  +"successful");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "This email permission denied");
        }
    }

    //put
    public ResponseEntity editEvent(EventPutDto editEventPutDTO, int id) {
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        Event event = eventRepository.findById(id).orElseThrow( ()->{
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        List<Event> checkCompare = eventRepository.findAllByEventCategoryEventCategoryId(event.getEventCategory().getEventCategoryId());
        int i = 0;
        int index = 0;
        for(Event b: checkCompare){
            if(b.getIdBooking() == id){
                index = i;
            }
            i++;
        }
        checkCompare.remove(index);
                modelMapper.map(editEventPutDTO , event);

        if ((!checkTimeOverLap(checkCompare , event))){
                 if(user.getRoleUser().equals("admin")){
                     eventRepository.saveAndFlush(event);
                     return ResponseEntity.status(200).body("Edited Successfully");
                 }
                 else if (user.getRoleUser().equals("student")){
                     if(!(event.getBookingEmail().equals(user.getEmailUser()))){
                         throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "email is not the same as student's email");
                     }
                     else{
                         eventRepository.saveAndFlush(event);
                         return ResponseEntity.status(200).body("Edited Successfully");
                     }
                 }
        }
        else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
        }
        throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "This email permission denied");
    }
}
