package sit.int221.TimeUpBackend.controller;


import io.jsonwebtoken.impl.DefaultClaims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.config.JwtTokenUtil;
import sit.int221.TimeUpBackend.entities.JwtRequest;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.UserRepository;
import sit.int221.TimeUpBackend.security.Argon2PasswordEncoder;
import sit.int221.TimeUpBackend.service.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    private Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {


        authenticate(authenticationRequest.getEmailUser(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmailUser());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

        User user = userRepository.findByEmailUser(authenticationRequest.getEmailUser());
        if(user != null){
            if(argon2PasswordEncoder.matches(authenticationRequest.getPassword() , user.getPassword())){
                authenticate(authenticationRequest.getEmailUser(), authenticationRequest.getPassword());
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getEmailUser());
                final String accessToken = jwtTokenUtil.generateToken(userDetails);
                final String refreshToken = jwtTokenUtil.doGenerateRefreshToken(userDetails.getUsername());
                Map<String , String> tokens = new HashMap<>();
                tokens.put("accessToken" , accessToken);
                tokens.put("refreshToken" , refreshToken);
                return ResponseEntity.ok(tokens);
            }
            else {
                return ResponseEntity.status(401).body("password or email invalid !");
            }
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }



    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    @RequestMapping(value = "/api/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity refreshtoken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("Authorization").substring(7);
       if(!(jwtTokenUtil.isTokenExpired(refreshToken))){
           String email = jwtTokenUtil.getUsernameFromToken(refreshToken);
           final UserDetails userDetails = userDetailsService
                   .loadUserByUsername(email);
           final String accessToken = jwtTokenUtil.generateToken(userDetails);
           Map<String , String> tokens = new HashMap<>();
           tokens.put("accessToken" , accessToken);
           tokens.put("refreshToken" , refreshToken);
           return ResponseEntity.ok(tokens);
       }
       throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Token Expire");
    }
}
