package sit.int221.TimeUpBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.dtos.CategoryOwnerDto;
import sit.int221.TimeUpBackend.dtos.EventCategoryDto;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.EventCategoryOwner;
import sit.int221.TimeUpBackend.service.EventCategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RequestMapping("api/admin/event-category")
public class EventCategoryController {
    @Autowired
    private EventCategoryService eventCategoryService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin' , 'student' , 'lecturer')")
    public List<EventCategory> getAllCategory(){
        return eventCategoryService.getAllCategory();
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventCategory createEventCategory(@Valid  @RequestBody EventCategory newEventCategory){
        return  eventCategoryService.create(newEventCategory);
    }

    @PostMapping("/category-owner")
    @PreAuthorize("hasAnyAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addEventCategoryOwner(@RequestBody CategoryOwnerDto categoryOwnerDto){
        return eventCategoryService.addCategoryOwner(categoryOwnerDto);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'lecturer')")
    public ResponseEntity editEventCategory(@Valid  @RequestBody EventCategoryDto editEventCategory , @PathVariable Integer id){
        return eventCategoryService.editEventCategory(editEventCategory , id);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity deleteCategoryOwner(@PathVariable Integer id){
        return eventCategoryService.deleteCategoryOwner(id);
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
