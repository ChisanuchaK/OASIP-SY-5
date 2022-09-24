package sit.int221.TimeUpBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String emailUser) throws UsernameNotFoundException {
        sit.int221.TimeUpBackend.entities.User user = userRepository.findByEmailUser(emailUser);
        List<SimpleGrantedAuthority> roles = null;
        if (user != null) {
            if (user.getRoleUser().equals("admin")) {
                roles = Arrays.asList(new SimpleGrantedAuthority("admin"));
                return new User( user.getEmailUser(),  user.getPassword(),
                        roles);
            }
            if (user.getRoleUser().equals("student")) {
                roles = Arrays.asList(new SimpleGrantedAuthority("student"));
                return new User( user.getEmailUser(),  user.getPassword(),
                        roles);
            }
            if (user.getRoleUser().equals("lecturer")) {
                roles = Arrays.asList(new SimpleGrantedAuthority("lecturer"));
                return new User( user.getEmailUser(),  user.getPassword(),
                        roles);
            }

        } else {
            throw new UsernameNotFoundException("User not found with user email: " + user.getEmailUser());
        }

        throw new UsernameNotFoundException("User not found with the name " + user);
    }

}
