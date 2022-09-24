package sit.int221.TimeUpBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.service.EventService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static sit.int221.TimeUpBackend.controller.EventCategoryController.getStringStringMap;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "*" , allowedHeaders = "*")

public class EventController {

    @Autowired
    private EventService eventService;
    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public List<EventDto> getAllEvent(){
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public EventMoreDetailDto getBEventById(@PathVariable Integer id){
        return eventService.getEventDetailDTOById(id);
    }

    @GetMapping("/{id}/category")
    @PreAuthorize("hasAuthority('admin')")
    public List<EventDto> getAllEventByIdCategory(@PathVariable Integer id){
        return eventService.getEventByIdCategory(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createEvent(@Valid @RequestBody EventPostDto newBooking){
        return eventService.create(newBooking);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public void deleteEventById(@PathVariable Integer id){
        eventService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public ResponseEntity editEvent(@Valid @RequestBody EventPutDto editBooking , @PathVariable int id ){
        return eventService.editEvent(editBooking , id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }
}


