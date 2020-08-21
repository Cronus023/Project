package by.project.first.service;


import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.controllers.ReqAndRes.FindNotPassedWorkersResponse;
import by.project.first.models.*;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<Iterable<OfficeModel>> get_offices_by_login (String login){
        UserModel provider = userRepo.findByLogin(login);
        Set<OfficeModel> providerOffices = officeRepo.findAllByLeaderID(provider);
        return ResponseEntity.ok(providerOffices);
    }

    public ResponseEntity<Message> become_provider (BecomeRequest request){
        UserModel user = userRepo.findByLogin(request.getLogin());
        OfficeModel office = request.getOffice();
        office.getLeaderID().add(user);
        officeRepo.save(office);
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Message> create_office (OfficeModel office){
        OfficeModel newOffice = officeRepo.findByName(office.getName());
        if(newOffice == null){
            officeRepo.save(office);
            return ResponseEntity.ok(new Message(""));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Such office already exist!"));
        }
    }

    public boolean checkOfficeApplications(ApplicationModel lastApplication){

        if(lastApplication == null){
            return true;
        }

        if(lastApplication.getStatus().equals("WAIT_FOR_AN_ANSWER")){
            return false;
        }

        if(lastApplication.getStatus().equals("REJECT")){
            return true;
        }

        if(lastApplication.getStatus().equals("ACCEPT")){
            Date dateOfEndOfPermission = lastApplication.getDateOfApplication();
            dateOfEndOfPermission.setYear(dateOfEndOfPermission.getYear() + 1);
            if(dateOfEndOfPermission.compareTo(new Date()) <= 0){
                return true;
            }
            if(dateOfEndOfPermission.compareTo(new Date()) > 0){
                return false;
            }
        }

        return false;
    }
    public ResponseEntity<Iterable<OfficeModel>> get_office(){
        Iterable<OfficeModel> offices = officeRepo.findAll();
        return ResponseEntity.ok(offices);
    }


    public ResponseEntity get_office_by_name (String name ){
        OfficeModel office = officeRepo.findByName(name);
        ApplicationModel lastApplication = office.getLastApplication();

        if(!checkOfficeApplications(lastApplication)){
            return ResponseEntity.status(400).body(new Message("Application already exist!"));
        }

        Set<WorkerModel> passedWorkers = office.getWorkerId();
        Set<WorkerModel> notPassedWorkers = new HashSet<>();

        passedWorkers.forEach(worker->{
            if(trainingRepo.findAllByTrainingPassedID(worker).isEmpty()){
                notPassedWorkers.add(worker);
            }
        });

        return ResponseEntity.ok(new FindNotPassedWorkersResponse(office, notPassedWorkers));
    }
}
