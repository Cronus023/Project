package by.project.first.controllers;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class WorkersController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/workers/delete")
    public ResponseEntity delete(){
        return ResponseEntity.ok(new Message("delete"));
    }
    @PostMapping("/workers/add")
    public ResponseEntity add(@RequestBody AddWorkerRequest request){
        workerService.saveWorker(request);
        return ResponseEntity.ok(new Message ("Ok!"));
    }

    @PostMapping("/workers/edit")
    public ResponseEntity edit(){
        return ResponseEntity.ok(new Message("edit"));
    }
}