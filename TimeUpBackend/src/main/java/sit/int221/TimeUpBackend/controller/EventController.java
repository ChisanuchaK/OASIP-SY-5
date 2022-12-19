package sit.int221.TimeUpBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.entities.Event;
import sit.int221.TimeUpBackend.messages.ResponseMessage;
import sit.int221.TimeUpBackend.repositories.EventRepository;
import sit.int221.TimeUpBackend.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static sit.int221.TimeUpBackend.controller.EventCategoryController.getStringStringMap;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "*" , allowedHeaders = "*")

public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private FilesStorageServiceImpl storageService;
    @GetMapping("/file/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Integer id) {
        Resource file = storageService.load(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin' , 'student' , 'lecturer')")
    public List<EventDto> getAllEvent(){
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student' , 'lecturer')")
    public EventMoreDetailDto getEventById(@PathVariable Integer id){
        return eventService.getEventDetailDTOById(id);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createEvent(@Valid @RequestPart("content") EventPostDto newBooking , @RequestParam(value = "file" , required = false) MultipartFile file){
        return eventService.create(newBooking , file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public void deleteEventById(@PathVariable Integer id){
        eventService.delete(id);
    }

    @DeleteMapping("/file/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public ResponseEntity deleteFile(@PathVariable Integer id) throws IOException {
        storageService.deleteById(id);
        return ResponseEntity.ok().body("Delete file success");
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public ResponseEntity editEvent(@Valid @RequestPart("content") EventPutDto editBooking , @RequestParam(value = "file" , required = false) MultipartFile multipartFile  , @PathVariable Integer id ) throws IOException {
        return eventService.editEvent(editBooking , multipartFile , id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }
}


