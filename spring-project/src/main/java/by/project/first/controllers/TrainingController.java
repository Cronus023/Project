package by.project.first.controllers;


import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.Message;
import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.TrainingRepo;
import by.project.first.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TrainingController {

    private final TrainingRepo trainingRepo;
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingRepo trainingRepo, TrainingService trainingService) {
        this.trainingRepo = trainingRepo;
        this.trainingService = trainingService;
    }

    @GetMapping("/training/getTrainings")
    public ResponseEntity<Iterable<TrainingModel>> getTrainings() {
        return ResponseEntity.ok(trainingRepo.findAll());
    }

    @GetMapping("/training/getTrainingById")
    public ResponseEntity<Optional<TrainingModel>> get_training_by_id(@RequestParam Long id) {
        return ResponseEntity.ok(trainingRepo.findById(id));
    }

    @PostMapping("/training/findWorkersByIdAndUserLogin")
    public Iterable<WorkerModel> findWorkersByIdAndUserLogin(@RequestBody GetWorkersTrainingRequest request) {
        return trainingService.findWorkersByIdAndUserLogin(request);
    }

    @PostMapping("/training/editTraining")
    public Message editTraining(@RequestBody TrainingModel request) {
        return trainingService.editTraining(request);
    }

    @PostMapping("/training/deleteWorkersInTraining")
    public Message deleteWorkersInTraining(@RequestBody RegWorkersToTraining request) {
        return trainingService.deleteWorkersInTraining(request);
    }

    @PostMapping("/training/deleteTraining")
    public Message deleteTraining(@RequestParam Long id) {
        return trainingService.deleteTraining(id);
    }

    @PostMapping("/training/findTrainingWorkers")
    public ResponseEntity findTrainingWorkers(@RequestBody GetWorkersTrainingRequest request) {
        return trainingService.findTrainingWorkers(request);
    }

    @PostMapping("/training/registerWorkers")
    public ResponseEntity<Message> registerWorkers(@RequestBody RegWorkersToTraining request) {
        return trainingService.registerWorkers(request);
    }

    @PostMapping("/training/addVisitors")
    public ResponseEntity<Message> addVisitors(@RequestBody RegWorkersToTraining request) {
        return trainingService.addVisitors(request);
    }

    @PostMapping("/training/addPassedWorkers")
    public Message addPassedWorkers(@RequestBody RegWorkersToTraining request) {
        return trainingService.addPassedWorkers(request);
    }

    @PostMapping("/training/add")
    public Message saveTraining(@RequestBody AddTrainingRequest request) {
        return trainingService.saveTraining(request);
    }

}
