package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTOS.EventDTO;
import sit.int221.TimeUpBackend.DTOS.UserDTOGET;
import sit.int221.TimeUpBackend.DTOS.UserDTOPOST;
import sit.int221.TimeUpBackend.DTOS.UserDTOPUT;
import sit.int221.TimeUpBackend.Entities.RoleUser;
import sit.int221.TimeUpBackend.Entities.User;
import sit.int221.TimeUpBackend.Repository.UserRepository;

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
   public  User getUserByID(Integer id){
       return userRepository.findById(id).orElseThrow(()->
               new ResponseStatusException(HttpStatus.NOT_FOUND));
   }

   //post
    public User createUser(UserDTOPOST userDTOPOST){
        User user = new User();
      if (userDTOPOST.getRoleUser().equals(RoleUser.values())){
          user.setNameUser(userDTOPOST.getNameUser().trim());
          user.setEmailUser(userDTOPOST.getEmailUser().trim());
          user.setRoleUser(userDTOPOST.getRoleUser());
          return userRepository.saveAndFlush(user);
      }
      else{
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "name or email some");
      }
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
