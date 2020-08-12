package by.project.first.controllers;


import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
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

    @PostMapping("/training/get_workers")
    public ResponseEntity get_workers(@RequestBody GetWorkersTrainingRequest request){
        Iterable<WorkerModel> workers= trainingService.findByIdAndUserLogin(request);
        return ResponseEntity.ok(workers);
    }


    @PostMapping("/training/register_workers")
    public ResponseEntity register_workers(@RequestBody RegWorkersToTraining request){
        Message message = trainingService.registerWorkers(request);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/training/add")
    public ResponseEntity add_training(@RequestBody AddTrainingRequest request){
        Message message = trainingService.saveTraining(request);
        return ResponseEntity.ok(message);
    }
}
