package by.project.first.service;


import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
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
    private WorkerRepo workerRepo;

    public ResponseEntity check_provider_office (String login, String officeName){
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

    public ResponseEntity check_worker_id (Long id){
        Optional<WorkerModel> worker = workerRepo.findById(id);
        if(worker.isEmpty()){
            return ResponseEntity.status(400).body(new Message("Wrong worker id!"));
        }
        return  ResponseEntity.ok(new Message("ok!"));
    }
}
