package sit.int221.TimeUpBackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.UserRepository;
import sit.int221.TimeUpBackend.security.Argon2PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();


    //get
    public List<UserGetDto> getUserByName(String nameUser) {
        List<User> user = userRepository.findAllByNameUserOrderByNameUserDesc(nameUser);
        return user.stream().map(e -> modelMapper.map(e, UserGetDto.class)).collect(Collectors.toList());
    }

    public ResponseEntity getAllUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFull = userRepository.findByEmailUser(userDetails.getUsername());
        if(userFull.getRoleUser().equals("admin")){
            List<User> user = userRepository.findAll();
             user.stream().map(e -> modelMapper.map(e, UserGetDto.class)).collect(Collectors.toList());
             return ResponseEntity.ok(user);
        }
        else if (userFull.getRoleUser().equals("student") && userFull.getEmailUser().equals(userDetails.getUsername())){
            User user = userRepository.findByEmailUser(userDetails.getUsername());
             modelMapper.map(user , UserGetDto.class);
            return ResponseEntity.ok(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public UserGetDto getUserByID(Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFull = userRepository.findByEmailUser(userDetails.getUsername());
        if(userFull.getRoleUser().equals("admin")){
            User user = userRepository.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND));
            return modelMapper.map(user, UserGetDto.class);
        }
        else if (userFull.getRoleUser().equals("student") && userFull.getEmailUser().equals(userDetails.getUsername())){
            if(id == userFull.getIdUser()){
                User user = userRepository.findById(userFull.getIdUser()).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
                return modelMapper.map(user, UserGetDto.class);
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND , "id not found or email permission denied");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }


    public User MatchUser(MatchDto matchDto) {
        User user = userRepository.findByEmailUser(matchDto.getEmailUser());
        if (user != null) {
            if ((encoder.matches(matchDto.getPassword(), user.getPassword())) && (matchDto.getEmailUser().equals(user.getEmailUser()))) {
                throw new ResponseStatusException(HttpStatus.OK, "password matched");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "password not matched");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }

    }

    public boolean checkDuplicate(String email , String name){
        if(userRepository.findByEmailUserOrNameUser(email , name) != null){
            return true;
        }
        else{
            return false;
        }
    }

    //post
    public ResponseEntity createUser(UserPostDto userDTOPOST) {
        if(checkDuplicate(userDTOPOST.getEmailUser() , userDTOPOST.getNameUser())){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "username or email duplicate");
        }else{
            String encodePassword = encoder.encode(userDTOPOST.getPassword());
            User user = new User();
            if (userDTOPOST.getRoleUser() == null) {
                user.setNameUser(userDTOPOST.getNameUser().trim());
                user.setEmailUser(userDTOPOST.getEmailUser().trim());
                user.setRoleUser("student");
                user.setPassword(encodePassword);
                 userRepository.saveAndFlush(user);
                return ResponseEntity.status(201).body("Create user Successfully");
            } else {
                user.setNameUser(userDTOPOST.getNameUser().trim());
                user.setEmailUser(userDTOPOST.getEmailUser().trim());
                user.setRoleUser(userDTOPOST.getRoleUser());
                user.setPassword(encodePassword);
                userRepository.saveAndFlush(user);
                return ResponseEntity.status(201).body("Create user Successfully");
            }
        }
    }


    //update
    public ResponseEntity updateUser(UserPutDto userDTOPUT, Integer id) {
        if((checkDuplicate(userDTOPUT.getEmailUser() , userDTOPUT.getNameUser())) && !(checkDuplicate(userDTOPUT.getEmailUser(), userDTOPUT.getNameUser())) ){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "username or email duplicate");
        }
       else{
           User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
           if ((userDTOPUT.getNameUser().equals(user.getNameUser()) && userDTOPUT.getEmailUser().equals(user.getEmailUser())
                   && (userDTOPUT.getRoleUser().equals(user.getRoleUser())))
           || !(userDTOPUT.getNameUser().equals(user.getNameUser()) && userDTOPUT.getEmailUser().equals(user.getEmailUser())
                   && (userDTOPUT.getRoleUser().equals(user.getRoleUser())))) {
               user.setNameUser(userDTOPUT.getNameUser().trim());
               user.setEmailUser(userDTOPUT.getEmailUser().trim());
//            user.setPassword(userDTOPUT.getPassword());
               user.setRoleUser(userDTOPUT.getRoleUser());
                userRepository.saveAndFlush(user);
               return ResponseEntity.status(200).body("Update user Successfully");
           } else {
               return ResponseEntity.status(400).body("Don't update user Successfully");
           }
       }
    }

    //delete
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }


}
