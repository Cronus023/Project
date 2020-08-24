package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.*;
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

    @GetMapping("/workers/get_workers")
    public ResponseEntity<Set<WorkerModel>> get_workers(@RequestParam String name){
        return workerService.get_workers(name);
    }

    @GetMapping("/workers/view_trainings")
    public ResponseEntity<Iterable<TrainingModel>> view_trainings(@RequestParam Long id){
        return workerService.view_trainings(id);
    }

    @GetMapping("/workers/get_worker_by_id")
    public ResponseEntity<WorkerModel> get_worker_by_id(@RequestParam Long id){
       return workerService.get_worker_by_id(id);
    }

    @PostMapping("/workers/delete_worker")
    public ResponseEntity<Message> delete_worker(@RequestBody DeleteWorkerRequest request){
        return workerService.deleteWorker(request);
    }

    @PostMapping("/workers/add_worker")
    public ResponseEntity<Message> add_worker(@RequestBody AddWorkerRequest request){
        return workerService.saveWorker(request);
    }

    @PostMapping("/workers/edit_worker")
    public ResponseEntity<Message> edit_worker(@RequestBody WorkerModel worker){
        return workerService.editWorker(worker);
    }
}