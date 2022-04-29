package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.Entity.EventCategory;
import sit.int221.TimeUpBackend.Service.EventCategoryService;

import java.util.List;

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
    public EventCategory createEventCategory(@RequestBody EventCategory newEventCategory){
        return  eventCategoryService.create(newEventCategory); 
    }
}
