package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@CrossOrigin(origins="http://localhost:8000")
public class WorkersController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private OfficeRepo officeRepo;
    @GetMapping("/workers/get_workers")
    public ResponseEntity get_workers(@RequestParam String name){
        OfficeModel office = officeRepo.findByName(name);
        if(office != null){
            return ResponseEntity.ok(office.getWorkerId());
        }
        return ResponseEntity.status(400).body(new Message("error"));
    }

    @PostMapping("/workers/delete")
    public ResponseEntity delete(@RequestBody DeleteWorkerRequest request){
        OfficeModel newOffice = workerService.deleteWorker(request);
        return ResponseEntity.ok(newOffice);
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