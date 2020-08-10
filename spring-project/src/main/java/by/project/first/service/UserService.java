package by.project.first.service;

import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel saveUser(UserModel userModel) {
        userModel.setActive(true);

        userModel.setRoles(userModel.getRoles());

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepo.save(userModel);
    }


    public UserModel findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public UserModel findByLoginAndPassword(String login, String password) {
        UserModel userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}