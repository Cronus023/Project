package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.*;
import by.project.first.repositories.OfficeRepo;
import by.project.first.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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

    @GetMapping("/workers/view_trainings")
    public ResponseEntity view_trainings(@RequestParam Long id){
        ResponseEntity response = workerService.view_trainings(id);
        return response;
    }

    @GetMapping("/workers/get_worker_by_id")
    public ResponseEntity get_workers(@RequestParam Long id){
        Optional<WorkerModel> worker = workerService.findById(id);
        if(worker == null){
            return ResponseEntity.status(400).body(new Message("error"));
        }
        else return ResponseEntity.ok(worker);
    }

    @PostMapping("/workers/delete")
    public ResponseEntity delete(@RequestBody DeleteWorkerRequest request){
        Iterable<TrainingModel> office= workerService.deleteWorker(request);
        return ResponseEntity.ok(office);
    }
    @PostMapping("/workers/add")
    public ResponseEntity add(@RequestBody AddWorkerRequest request){
        workerService.saveWorker(request);
        return ResponseEntity.ok(new Message ("Ok!"));
    }

    @PostMapping("/workers/edit")
    public ResponseEntity edit(@RequestBody WorkerModel worker){
        workerService.editWorker(worker);
        return ResponseEntity.ok(new Message("edit"));
    }
}