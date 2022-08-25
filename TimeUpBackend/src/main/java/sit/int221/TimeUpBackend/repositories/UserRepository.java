package sit.int221.TimeUpBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByNameUserOrderByNameUserDesc(String nameUser);
    User findByEmailUser(String email);
}
