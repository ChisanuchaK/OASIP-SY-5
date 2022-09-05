package sit.int221.TimeUpBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.util.ArrayList;
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String emailUser) throws UsernameNotFoundException {
        sit.int221.TimeUpBackend.entities.User user = userRepository.findByEmailUser(emailUser);
        if (user != null) {
            return new User( user.getEmailUser(),  user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with user email: " + user.getEmailUser());
        }
    }
}
