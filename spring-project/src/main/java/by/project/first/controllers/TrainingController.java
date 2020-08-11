package by.project.first.controllers;


import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.models.Message;
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

    @PostMapping("/training/add")
    public ResponseEntity add_training(@RequestBody AddTrainingRequest request){
        Message message = trainingService.saveTraining(request);
        return ResponseEntity.ok(message);
    }
}
