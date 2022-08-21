package sit.int221.TimeUpBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.TimeUpBackend.Entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByNameUserOrderByNameUserDesc(String nameUser);
}
