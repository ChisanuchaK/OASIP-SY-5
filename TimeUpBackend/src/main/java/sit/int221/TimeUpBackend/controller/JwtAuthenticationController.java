package sit.int221.TimeUpBackend.controller;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.config.CookieUtil;
import sit.int221.TimeUpBackend.config.SecurityCipher;
import sit.int221.TimeUpBackend.dtos.LoginRequest;
import sit.int221.TimeUpBackend.dtos.LoginResponse;
import sit.int221.TimeUpBackend.service.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class JwtAuthenticationController {

    @Autowired
    private UserService userService;

    private String accessTokenCookie = "accessToken";
    private String refreshTokenCookie = "refreshToken";

    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    @GetMapping(value = "/api/refreshtoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
        return userService.refresh(decryptedRefreshToken);
    }

    @GetMapping("/api/logout")
    public ResponseEntity logOut(HttpServletResponse httpServletResponse){
        CookieUtil.clear(httpServletResponse,accessTokenCookie);
        CookieUtil.clear(httpServletResponse,refreshTokenCookie);
        return  ResponseEntity.ok("Clear token success") ;
    }
}


