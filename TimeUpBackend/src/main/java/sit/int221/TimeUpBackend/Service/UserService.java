package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTOS.RoleUserDTO;
import sit.int221.TimeUpBackend.DTOS.UserDTOGET;
import sit.int221.TimeUpBackend.DTOS.UserDTOPOST;
import sit.int221.TimeUpBackend.DTOS.UserDTOPUT;
import sit.int221.TimeUpBackend.Entities.RoleUser;
import sit.int221.TimeUpBackend.Entities.User;
import sit.int221.TimeUpBackend.Repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private  UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //get
    public List<UserDTOGET> getUserByName(String nameUser){
        List<User> user = userRepository.findAllByNameUserOrderByNameUserDesc(nameUser);
        return user.stream().map(e -> modelMapper.map(e, UserDTOGET.class)).collect(Collectors.toList());
   }
   public List<User> getAllUser(){
    return userRepository.findAll();
   }

   public  UserDTOGET getUserByID(Integer id){
       User user =  userRepository.findById(id).orElseThrow(()->
               new ResponseStatusException(HttpStatus.NOT_FOUND));

       return  modelMapper.map(user , UserDTOGET.class );
   }
   public ArrayList<RoleUser> getAllRole(){
        RoleUser[] roleUser   = RoleUser.values();
       return (ArrayList<RoleUser>) Arrays.stream(roleUser).collect(Collectors.toList());
   }
   //post
    public User createUser(UserDTOPOST userDTOPOST) {
        RoleUser[] roleUser = RoleUser.values();
        User user = new User();
        if (userDTOPOST.getRoleUser() == null){
            user.setNameUser(userDTOPOST.getNameUser().trim());
            user.setEmailUser(userDTOPOST.getEmailUser().trim());
            user.setRoleUser(RoleUser.student);
            return userRepository.saveAndFlush(user);
        }
        else {
            for (int i = 0 ; i < roleUser.length ; i++){
                if (roleUser[i].equals(userDTOPOST.getRoleUser())){
                    user.setNameUser(userDTOPOST.getNameUser().trim());
                    user.setEmailUser(userDTOPOST.getEmailUser().trim());
                    user.setRoleUser(userDTOPOST.getRoleUser());
                    System.out.println(userDTOPOST.getRoleUser());
                    return userRepository.saveAndFlush(user);
                }
            }
        }
           return null;
            }



    //update
    public User updateUser(UserDTOPUT  userDTOPUT  , Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!(userDTOPUT.getNameUser().equals(user.getNameUser())&& userDTOPUT.getEmailUser().equals(user.getEmailUser()))){
            user.setNameUser(userDTOPUT.getNameUser().trim());
            user.setEmailUser(userDTOPUT.getEmailUser().trim());
            return userRepository.saveAndFlush(user);
        }
        else{
            return user;
        }
    }

    //delete
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public void deleteAllUser(){userRepository.deleteAll();}

}
