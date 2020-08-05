package by.project.first.repositories;

import by.project.first.models.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserModel, Long> {
    UserModel findByLogin(String login);
}