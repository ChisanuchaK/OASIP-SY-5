package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.DTOS.UserDTOPOST;
import sit.int221.TimeUpBackend.Entities.EventCategory;
import sit.int221.TimeUpBackend.Entities.User;
import sit.int221.TimeUpBackend.Service.EventCategoryService;
import sit.int221.TimeUpBackend.Service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllUser(){
        return  userService.getAllUser();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUser( @RequestBody UserDTOPOST userDTOPOST){
        return  userService.createUser(userDTOPOST);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id){
        userService.deleteUser(id);
    }
}
