package sit.int221.TimeUpBackend.security;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2PasswordEncoder  {

    private static final Argon2PasswordEncoder ARGON_2_PASSWORD_ENCODER = new Argon2PasswordEncoder();

    public Argon2PasswordEncoder argon2PasswordEncoder(){
        return ARGON_2_PASSWORD_ENCODER;
    }

    public String encode(final CharSequence rawPassword) {
        return ARGON_2_PASSWORD_ENCODER.encode(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ARGON_2_PASSWORD_ENCODER.matches(encodedPassword, rawPassword.toString());
    }
}
