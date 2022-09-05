package sit.int221.TimeUpBackend;

import de.mkammerer.argon2.Argon2Factory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TimeUpBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeUpBackendApplication.class, args);

	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder();
	}

}
