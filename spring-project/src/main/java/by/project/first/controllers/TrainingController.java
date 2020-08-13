package by.project.first.controllers;


import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.*;
import by.project.first.repositories.TrainingRepo;
import by.project.first.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return trainingService.findByIdAndUserLogin(request);
    }

    @PostMapping("/training/edit_training")
    public ResponseEntity edit_training(@RequestBody TrainingModel request){
        return trainingService.edit_training(request);
    }

    @PostMapping("/training/delete_workers_in_training")
    public ResponseEntity delete_workers_in_training(@RequestBody RegWorkersToTraining request){
        return trainingService.delete_workers(request);
    }

    @PostMapping("/training/delete_training")
    public ResponseEntity delete_training(@RequestParam Long id){
        return trainingService.delete_training(id);
    }

    @PostMapping("/training/get_visit_and_passing")
    public ResponseEntity get_visit_and_passing(@RequestBody GetWorkersTrainingRequest request){
        return trainingService.findTrainingWorkers(request);
    }

    @PostMapping("/training/register_workers")
    public ResponseEntity register_workers(@RequestBody RegWorkersToTraining request){
        return trainingService.registerWorkers(request);
    }

    @PostMapping("/training/add_visitors")
    public ResponseEntity add_visitors(@RequestBody RegWorkersToTraining request){
        return trainingService.addVisitors(request);
    }

    @PostMapping("/training/add_passed")
    public ResponseEntity add_passed(@RequestBody RegWorkersToTraining request){
        return trainingService.addPassedWorkers(request);
    }


    @PostMapping("/training/add")
    public ResponseEntity add_training(@RequestBody AddTrainingRequest request){
        return trainingService.saveTraining(request);
    }
}
