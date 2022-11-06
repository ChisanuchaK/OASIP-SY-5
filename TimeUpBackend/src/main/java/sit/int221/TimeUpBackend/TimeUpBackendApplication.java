package sit.int221.TimeUpBackend;

import de.mkammerer.argon2.Argon2Factory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sit.int221.TimeUpBackend.service.FilesStorageService;

import javax.annotation.Resource;

@SpringBootApplication
public class TimeUpBackendApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(TimeUpBackendApplication.class, args);
	}

	public void run(String... arg) throws Exception {
		storageService.init();
	}

}
