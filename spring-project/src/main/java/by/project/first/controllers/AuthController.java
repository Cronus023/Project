package by.project.first.controllers;

import by.project.first.models.Message;
import by.project.first.models.UserModel;
import by.project.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserModel user){
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity register( @RequestBody UserModel user){
        return userService.register(user);
    }

    @GetMapping("/authLogout")
    public ResponseEntity authLogout(@RequestParam String login){
        return userService.logout(login);
    }

    @GetMapping("/auth/check")
    public ResponseEntity checkAuth(@RequestParam String token){
        return userService.checkAuth(token);
    }
}
