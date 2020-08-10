package by.project.first.controllers;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.controllers.ReqAndRes.LoginResponse;
import by.project.first.models.Message;
import by.project.first.models.Token;
import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import by.project.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserModel user){
        UserModel authUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if(authUser != null){
            String token = jwtProvider.generateToken(authUser.getLogin());
            return ResponseEntity.ok(new LoginResponse(token, authUser.getRoles()));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Please, check your date"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity register( @RequestBody UserModel user){

        UserModel newUser = userRepo.findByLogin(user.getLogin());
        if(newUser == null){
            userService.saveUser(user);
            return ResponseEntity.ok(new Message(""));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Such user already exist!"));
        }
    }


    @GetMapping("/authLogout")
    public ResponseEntity authLogout(@RequestParam String login){

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

}
