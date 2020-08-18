package by.project.first.service;


import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.controllers.ReqAndRes.FindNotPassedWorkersResponse;
import by.project.first.models.*;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.repositories.ApplicationRepo;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OfficeService {


    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity get_offices_by_login (String login){
        UserModel provider = userRepo.findByLogin(login);
        if(provider == null){
            return ResponseEntity.status(400).body(new Message("Wrong provider login!"));
        }
        Iterable<OfficeModel> offices = officeRepo.findAll();
        Set<OfficeModel> providerOffices = new HashSet<>();

        offices.forEach(office->{
            if(office.getLeaderID().contains(provider)){
                providerOffices.add(office);
            }
        });
        return ResponseEntity.ok(providerOffices);
    }

    public ResponseEntity become_provider (BecomeRequest request){
        UserModel user = userRepo.findByLogin(request.getLogin());
        OfficeModel office = request.getOffice();
        office.getLeaderID().add(user);
        officeRepo.save(office);
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity create_office (OfficeModel office){
        OfficeModel newOffice = officeRepo.findByName(office.getName());
        if(newOffice == null){
            officeRepo.save(office);
            return ResponseEntity.ok(new Message(""));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Such office already exist!"));
        }
    }

    public ResponseEntity get_office_by_name (String name ){
        OfficeModel office = officeRepo.findByName(name);
        if(office == null){
            return ResponseEntity.status(400).body(new Message("Can not find office"));
        }
        Iterable<ApplicationModel> applications = applicationRepo.findAll();

        for (ApplicationModel application : applications) {
            if (application.getOffice().getName().equals(name)) {
                return ResponseEntity.status(400).body(new Message("Application already exist!"));
            }
        }

        Set<WorkerModel> notPassedWorkers = office.getWorkerId();
        notPassedWorkers.forEach(worker->{
            if(trainingRepo.findAllByTrainingPassedID(worker) != null){
                notPassedWorkers.remove(worker);
            }
        });

        return ResponseEntity.ok(new FindNotPassedWorkersResponse(office, notPassedWorkers));
    }
}
