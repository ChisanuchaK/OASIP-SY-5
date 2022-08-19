package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTOS.UserDTOPOST;
import sit.int221.TimeUpBackend.DTOS.UserDTOPUT;
import sit.int221.TimeUpBackend.Entities.User;
import sit.int221.TimeUpBackend.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    //get
    public List<User> getAllUser(){
    return userRepository.findAll();
   }

   public  User getUserByID(Integer id){
       return userRepository.findById(id).orElseThrow(()->
               new ResponseStatusException(HttpStatus.NOT_FOUND));
   }

   //post
    public User createUser(UserDTOPOST userDTOPOST){
       User user = modelMapper.map(userDTOPOST , User.class);
            user.setNameUser(userDTOPOST.getNameUser().trim());
            user.setEmailUser(userDTOPOST.getEmailUser().trim());
            user.setRoleUser(userDTOPOST.getRoleUser());
          return  userRepository.saveAndFlush(user);
    }

    //update
    public User updateUser(UserDTOPUT  userDTOPUT  , Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(userDTOPUT , User.class);
        user.setNameUser(userDTOPUT.getNameUser().trim());
        user.setEmailUser(userDTOPUT.getEmailUser().trim());
        if (userDTOPUT.getNameUser() == user.getNameUser() && userDTOPUT.getEmailUser() == user.getEmailUser()){
            System.out.println("Edit up to date !!");
        }
        else{
            return userRepository.saveAndFlush(user);
        }
        return user;
    }

    //delete
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

}
