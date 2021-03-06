package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.DTOS.EventCategoryDTO;
import sit.int221.TimeUpBackend.Entities.EventCategory;
import sit.int221.TimeUpBackend.Service.EventCategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/event-category")
public class EventCategoryController {
    @Autowired
    private EventCategoryService eventCategoryService;

    @GetMapping("")
    public List<EventCategory> getAllCategory(){
        return eventCategoryService.getAllCategory();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public EventCategory createEventCategory(@Valid  @RequestBody EventCategory newEventCategory){
        return  eventCategoryService.create(newEventCategory);
    }


    @PutMapping("/{id}")
    public ResponseEntity editEventCategory(@Valid  @RequestBody EventCategoryDTO editEventCategory , @PathVariable Integer id){
        return eventCategoryService.editEventCategory(editEventCategory , id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }

    static Map<String, String> getStringStringMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
