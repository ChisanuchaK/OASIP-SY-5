package sit.int221.TimeUpBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.service.EmailServiceImpl;
import sit.int221.TimeUpBackend.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static sit.int221.TimeUpBackend.controller.EventCategoryController.getStringStringMap;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public ResponseEntity getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin' , 'student')")
    public UserGetDto getUserByID(@PathVariable int id){
        return  userService.getUserByID(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUser(@Valid @RequestBody UserPostDto userDTOPOST){

        return  userService.createUser(userDTOPOST);
    }


    @PostMapping("/match")
    public User MatchUser(@Valid @RequestBody MatchDto matchDto){
        return  userService.MatchUser(matchDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity updateUser(@Valid @RequestBody UserPutDto userDTOPUT , @PathVariable Integer id){
        return userService.updateUser(userDTOPUT , id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public void deleteUserById(@PathVariable Integer id){
        userService.deleteUser(id);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public void deleteAllUser(){userService.deleteAllUser();}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }






}
