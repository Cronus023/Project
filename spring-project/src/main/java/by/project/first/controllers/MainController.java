package by.project.first.controllers;

import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/main")
    public ResponseEntity main(){
        Iterable<UserModel> messages;
        messages = userRepo.findAll();

        return ResponseEntity.ok(messages);
    }
}
