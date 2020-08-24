package by.project.first.service;


import by.project.first.models.*;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class GuardService {
    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private ApplicationRepo applicationRepo;


    public ResponseEntity<Message> check_provider_office (String login, String officeName){
        OfficeModel office = officeRepo.findByName(officeName);
        if(office == null){
            return ResponseEntity.status(400).body(new Message("badStatus1"));
        }

        UserModel provider = userRepo.findByLogin(login);
        if(provider == null){
            return ResponseEntity.status(400).body(new Message("badStatus2"));
        }

        Set<UserModel> officeProviders = office.getLeaderID();
        if(officeProviders.contains(provider)){
            return  ResponseEntity.ok(new Message("ok!"));
        }
        return  ResponseEntity.ok(new Message("bad!"));
    }

    public ResponseEntity<Message> check_worker_id (Long id){
        Optional<WorkerModel> worker = workerRepo.findById(id);
        if(worker.isEmpty()){
            return ResponseEntity.status(400).body(new Message("Wrong worker id!"));
        }
        return  ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Message> check_training_control (Long id, String Login){
        Optional<TrainingModel> training = trainingRepo.findById(id);
        if(training.isEmpty()){
            return ResponseEntity.status(400).body(new Message("badStatus1"));
        }
        UserModel training_operator = userRepo.findByLogin(Login);
        if(training_operator == null){
            return ResponseEntity.status(400).body(new Message("badStatus2"));
        }

        if(training.get().getTrainerID() != training_operator){
            return ResponseEntity.ok(new Message("bad!"));
        }
        return ResponseEntity.ok(new Message("ok!"));
    }
    public ResponseEntity<Message> check_application (Long id){
        Optional<ApplicationModel> application = applicationRepo.findById(id);
        if(application.isEmpty()){
            return ResponseEntity.status(400).body(new Message("Application does not exist!"));
        }
        return ResponseEntity.ok(new Message("ok!"));
    }
}
