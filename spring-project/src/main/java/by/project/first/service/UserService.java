package by.project.first.service;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.controllers.ReqAndRes.LoginResponse;
import by.project.first.models.Message;
import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel saveUser(UserModel userModel) {
        userModel.setActive(true);
        userModel.setRoles(userModel.getRoles());
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepo.save(userModel);
    }

    public ResponseEntity login (UserModel user){
        UserModel authUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if(authUser != null){
            String token = jwtProvider.generateToken(authUser.getLogin());
            return ResponseEntity.ok(new LoginResponse(token, authUser.getRoles()));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Please, check your date"));
        }
    }

    public ResponseEntity get_roles (String login){
        UserModel user = userRepo.findByLogin(login);
        if(user == null){
            return ResponseEntity.status(400).body(new Message("Can not find user"));
        }
        return ResponseEntity.ok(user.getRoles());
    }
    public ResponseEntity logout (String login){
        UserModel user = userRepo.findByLogin(login);
        if(user == null){
            return ResponseEntity.status(400).body(new Message("error"));
        }
        else{
            user.setActive(false);
            userRepo.save(user);
            return ResponseEntity.ok(new Message("ok"));
        }
    }

    public ResponseEntity register (UserModel user){
        UserModel newUser = userRepo.findByLogin(user.getLogin());
        if(newUser == null){
            userService.saveUser(user);
            return ResponseEntity.ok(new Message(""));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Such user already exist!"));
        }
    }

    public UserModel findByLoginAndPassword(String login, String password) {
        UserModel user = userRepo.findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public ResponseEntity checkAuth(String token) {
        boolean check = jwtProvider.validateToken(token);
        if(check){
            return ResponseEntity.ok(new Message("ok!"));
        }
        else return ResponseEntity.status(400).body(new Message("bad token!"));
    }
}