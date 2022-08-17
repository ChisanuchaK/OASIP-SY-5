package sit.int221.TimeUpBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
