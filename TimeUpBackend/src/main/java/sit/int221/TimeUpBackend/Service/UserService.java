package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sit.int221.TimeUpBackend.DTOS.UserDTOPOST;
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

   //post
    public ResponseEntity createUser(UserDTOPOST userDTOPOST){
       User user = modelMapper.map(userDTOPOST , User.class);
            user.setNameUser(userDTOPOST.getNameUser());
            user.setEmailUser(userDTOPOST.getEmailUser());
            user.setRoleUser(userDTOPOST.getRoleUser());
            userRepository.saveAndFlush(user);
        return ResponseEntity.status(201).body("create User Successfully");
    }

    //delete
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

}
