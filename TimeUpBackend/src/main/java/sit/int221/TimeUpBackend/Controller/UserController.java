package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.DTOS.UserDTOGET;
import sit.int221.TimeUpBackend.DTOS.UserDTOPOST;
import sit.int221.TimeUpBackend.DTOS.UserDTOPUT;
import sit.int221.TimeUpBackend.Entities.User;
import sit.int221.TimeUpBackend.Service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static sit.int221.TimeUpBackend.Controller.EventCategoryController.getStringStringMap;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{name-user}")
    public List<UserDTOGET> getUserByName(@Valid @RequestParam String nameUser){
        return  userService.getUserByName(nameUser);
    }
    @GetMapping("")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable Integer id){return  userService.getUserByID(id);}

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser( @Valid @RequestBody UserDTOPOST userDTOPOST){
        return  userService.createUser(userDTOPOST);
    }

    @PutMapping("/{id}")
    public User updateUser(@Valid @RequestBody UserDTOPUT userDTOPUT , @PathVariable Integer id){
        return userService.updateUser(userDTOPUT , id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id){
        userService.deleteUser(id);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }




}
