package by.project.first.controllers;


import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.*;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class TrainingController {

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private TrainingService trainingService;

    @GetMapping("/training/get_trainings")
    public ResponseEntity get_trainings(){
        return ResponseEntity.ok(trainingRepo.findAll());
    }

    @GetMapping("/training/get_training_by_id")
    public ResponseEntity get_training_by_id(@RequestParam Long id){
        return ResponseEntity.ok(trainingRepo.findById(id));
    }
    @PostMapping("/training/get_workers")
    public ResponseEntity get_workers(@RequestBody GetWorkersTrainingRequest request){
        Iterable<WorkerModel> workers= trainingService.findByIdAndUserLogin(request);
        return ResponseEntity.ok(workers);
    }
    @PostMapping("/training/edit_training")
    public ResponseEntity edit_training(@RequestBody TrainingModel request){
        Long id = request.getId();
        Optional<TrainingModel> training = trainingRepo.findById(id);
        if(training.isEmpty()){
            ResponseEntity.status(400).body(new Message("This training does not exist!"));
        }
        trainingRepo.save(request);
        return ResponseEntity.ok(new Message("ok!"));
    }

    @PostMapping("/training/delete_training")
    public ResponseEntity delete_training(@RequestParam Long id){
        Optional<TrainingModel> training= trainingRepo.findById(id);
        if(training.isEmpty()){
            return ResponseEntity.status(400).body(new Message("Can not find training"));
        }
        else trainingRepo.deleteById(id);
        return ResponseEntity.ok(new Message("ok!"));
    }

    @PostMapping("/training/get_visit_and_passing")
    public ResponseEntity get_visit_and_passing(@RequestBody GetWorkersTrainingRequest request){
        ResponseEntity response= trainingService.findTrainingWorkers(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/training/register_workers")
    public ResponseEntity register_workers(@RequestBody RegWorkersToTraining request){
        Message message = trainingService.registerWorkers(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/training/add_visitors")
    public ResponseEntity add_visitors(@RequestBody RegWorkersToTraining request){
        ResponseEntity message = trainingService.addVisitors(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/training/add_passed")
    public ResponseEntity add_passed(@RequestBody RegWorkersToTraining request){
        ResponseEntity message = trainingService.addPassedWorkers(request);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/training/add")
    public ResponseEntity add_training(@RequestBody AddTrainingRequest request){
        Message message = trainingService.saveTraining(request);
        return ResponseEntity.ok(message);
    }
}
