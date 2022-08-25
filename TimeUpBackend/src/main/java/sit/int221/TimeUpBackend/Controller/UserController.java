package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTOS.*;
import sit.int221.TimeUpBackend.Entities.RoleUser;
import sit.int221.TimeUpBackend.Entities.User;
import sit.int221.TimeUpBackend.Service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sit.int221.TimeUpBackend.Controller.EventCategoryController.getStringStringMap;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public UserDTOGET getUserByID(@PathVariable int id){return  userService.getUserByID(id);}

    @GetMapping("/role")
    public ArrayList<RoleUser> getRole(){
        return userService.getAllRole();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody UserDTOPOST userDTOPOST){
        return  userService.createUser(userDTOPOST);
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginDTO loginDTO){
        return  userService.LogInUser(loginDTO);
    }

    @PutMapping("/{id}")
    public User updateUser(@Valid @RequestBody UserDTOPUT userDTOPUT , @PathVariable Integer id){
        return userService.updateUser(userDTOPUT , id);
    }


    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id){
        userService.deleteUser(id);
    }

    @DeleteMapping("")
    public void deleteAllUser(){userService.deleteAllUser();}
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }






}
