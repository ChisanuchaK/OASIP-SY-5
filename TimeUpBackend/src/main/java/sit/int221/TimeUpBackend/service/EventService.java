package sit.int221.TimeUpBackend.service;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.entities.*;
import sit.int221.TimeUpBackend.messages.ResponseMessage;
import sit.int221.TimeUpBackend.repositories.EventCategoryOwnerRepository;
import sit.int221.TimeUpBackend.repositories.EventRepository;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    private EventCategoryOwnerRepository eventCategoryOwnerRepository;

    @Autowired
    FilesStorageServiceImpl storageService;

    private ModelMapper modelMapper = new ModelMapper();

    private static  int number;
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
        } else if (user.getRoleUser().equals("lecturer")) {
            ArrayList<Event> bookingFromLecturerOwner = new ArrayList<>();
            List<EventCategoryOwner> eventCategoryOwner = eventCategoryOwnerRepository.findAllByUserIduser(user);
            for(int i = 0 ; i < eventCategoryOwner.size() ; i ++){
                bookingFromLecturerOwner.addAll(eventRepository.findAllByEventCategoryEventCategoryId(eventCategoryOwner.get(i).getEventcategoryEventcategory().getEventCategoryId()));
            }
          return bookingFromLecturerOwner.stream().map(e -> modelMapper.map(e, EventDto.class)).collect(Collectors.toList());
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
        else if (user.getRoleUser().equals("lecturer")){
            List<EventCategoryOwner> eventCategoryOwner = eventCategoryOwnerRepository.findAllByUserIduser(user);
            ArrayList<Integer> arrayIdCategory = new ArrayList<>();
            for (int i = 0 ; i < eventCategoryOwner.size() ; i ++){
                arrayIdCategory.add(eventCategoryOwner.get(i).getEventcategoryEventcategory().getEventCategoryId());
            }
            if(arrayIdCategory.indexOf(event.getEventCategory().getEventCategoryId()) != -1){
                Event eventDetail = eventRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
                return modelMapper.map(eventDetail , EventMoreDetailDto.class);
            }
            }
         throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }




    public ResponseEntity create( EventPostDto eventPostDto  ,  MultipartFile file ) {
        int sizeByte = 0;
        if(file != null){
             sizeByte =  (int)Math.floor(file.getSize()) ;
        }
        User checkUserByEmail = userRepository.findByEmailUser(eventPostDto.getBookingEmail());
        Event booking = modelMapper.map(eventPostDto, Event.class);
        EventCategory eventCategory = eventCategoryRepository.findById(eventPostDto.getEventCategory().getEventCategoryId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Event> checkCompare = eventRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser"){
            if (checkUserByEmail == null) {
                booking.setEventDuration(eventCategory.getEventDuration());
                booking.setBookingEmail(eventPostDto.getBookingEmail());
                booking.setFileSize(sizeByte);
                if (!checkTimeOverLap(checkCompare, booking)) {
                    eventRepository.save(booking);
                    Thread t = new Thread(()->{
                        emailService.sendMailWithAttachment(eventPostDto);
                    });
                    t.start();
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
                }
                return CreateEventAttribute(eventPostDto, file, booking, checkCompare);
            }
        }
        else {
            UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
            if (user.getRoleUser().equals("admin")) {
                if (checkUserByEmail != null) {
                    booking.setFileSize(sizeByte);
                    booking.setEventDuration(eventCategory.getEventDuration());
                    booking.setBookingEmail(eventPostDto.getBookingEmail());
                    booking.setUserIduser(checkUserByEmail);
                    if (!checkTimeOverLap(checkCompare, booking)) {
                        eventRepository.save(booking);
                        Thread t = new Thread(()->{
                            emailService.sendMailWithAttachment(eventPostDto);
                        });
                        t.start();
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
                    }

                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email not register");
                }
                return CreateEventAttribute(eventPostDto, file, booking, checkCompare);
            } else if (user.getRoleUser().equals("student") && user.getEmailUser().equals(checkUserByEmail.getEmailUser())) {
                if (checkUserByEmail != null) {
                    booking.setFileSize(sizeByte);
                    booking.setEventDuration(eventCategory.getEventDuration());
                    booking.setBookingEmail(checkUserByEmail.getEmailUser());
                    booking.setUserIduser(checkUserByEmail);
                    if (!checkTimeOverLap(checkCompare, booking)) {
                        eventRepository.save(booking);
                        Thread t = new Thread(()->{
                            emailService.sendMailWithAttachment(eventPostDto);
                        });
                        t.start();
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
                    }
                }else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email not register");
                }

                return CreateEventAttribute(eventPostDto, file, booking, checkCompare);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "booking email must be the same as the student's email");
    }

    private ResponseEntity CreateEventAttribute(EventPostDto eventPostDto, MultipartFile file, Event booking, List<Event> checkCompare) {

            number = booking.getIdBooking();
            if (!checkTimeOverLap(checkCompare, booking)) {
                String message = "";
//                if(event == null){
//                    storageService.save(file , number);
//                    eventRepository.save(booking);
//                    emailService.sendMailWithAttachment(eventPostDto);
//                    message = "Uploaded the file and create event successfully: " + file.getOriginalFilename();
//                }
                storageService.save(file, number);
                emailService.sendMailWithAttachment(eventPostDto);
                eventRepository.save(booking);
                    message = "Booking successful ";

                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlapped with other events");
            }
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
            try {
                storageService.deleteById(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            eventRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.OK , "Delete" + " " + id +  " " + user.getEmailUser() + " "  +"successful");
        }
        else if (user.getRoleUser().equals("student")){
            if(!(event.getBookingEmail().equals(user.getEmailUser()))){
                throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "email is not the same as student's email");
            }
            else{
                try {
                    storageService.deleteById(id);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                eventRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.OK , "Delete" + " " + id +  " " + user.getEmailUser() + " "  +"successful");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "This email permission denied");
        }
    }

    //put
    @SneakyThrows
    public ResponseEntity editEvent(EventPutDto editEventPutDTO, MultipartFile multipartFile , Integer id ) {
        int sizeByte = 0;
        if(multipartFile != null){
            sizeByte =  (int)Math.floor(multipartFile.getSize()) ;
        }
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        Event event = eventRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

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
                     System.out.println(event.getFileName());
                     System.out.println(event.getBookingEmail());
                     return conditionEditEvent(editEventPutDTO , multipartFile, id, sizeByte, event);
                 }
                 else if (user.getRoleUser().equals("student") && event.getBookingEmail().equals(getCurrentAuthentication.getUsername())){
                     if(!(event.getBookingEmail().equals(user.getEmailUser()))){
                         throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "email is not the same as student's email");
                     }
                     else{
                         return conditionEditEvent(editEventPutDTO , multipartFile, id, sizeByte, event);
                     }
                 }
        }
        else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
        }
        throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "This email permission denied");
    }

    private ResponseEntity conditionEditEvent(EventPutDto editEventPutDTO , MultipartFile multipartFile, Integer id, int sizeByte, Event event) throws IOException {
        if(multipartFile != null && event.getBookingEmail() == null){
            System.out.println(1);
            storageService.save(multipartFile , id);
            event.setFileSize(sizeByte);
            eventRepository.saveAndFlush(event);
        }
        else {
            if(multipartFile == null && editEventPutDTO.getFileName().equals(event.getFileName())){
                System.out.println(2);
                eventRepository.saveAndFlush(event);
            }
            else {
                System.out.println(3);
                storageService.deleteById(id);
                storageService.save(multipartFile , id);
                event.setFileSize(sizeByte);
                eventRepository.saveAndFlush(event);
            }
        }
        return ResponseEntity.status(200).body("Edited Successfully");
    }
}
