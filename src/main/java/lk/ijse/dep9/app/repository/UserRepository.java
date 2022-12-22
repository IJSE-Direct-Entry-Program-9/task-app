package lk.ijse.dep9.app.repository;

import lk.ijse.dep9.app.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
