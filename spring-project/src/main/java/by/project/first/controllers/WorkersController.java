package by.project.first.controllers;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class WorkersController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/workers/delete")
    public ResponseEntity create(){
        return ResponseEntity.ok(new Message("delete"));
    }
    @PostMapping("/workers/add")
    public ResponseEntity main(){
        return ResponseEntity.ok(new Message("add"));
    }
    @PostMapping("/workers/edit")
    public ResponseEntity become(){
        return ResponseEntity.ok(new Message("edit"));
    }
}