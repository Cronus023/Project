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

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
        Iterable<OfficeModel> providerOffices = officeRepo.findAllByLeaderID(provider);
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

    public boolean checkOfficeApplications(Set<ApplicationModel> officeApplications){

        AtomicBoolean flagOfWait = new AtomicBoolean(false);
        AtomicBoolean flagOfAccept = new AtomicBoolean(false);
        AtomicReference<Date> date = new AtomicReference<>(new Date());

        officeApplications.forEach(application->{
            if(application.getStatus().equals("WAIT_FOR_AN_ANSWER")){
                flagOfWait.set(true);
            }
            if(application.getStatus().equals("ACCEPT")){
                Date dateOfEndOfPermission = application.getOffice().getDateOfLastPermission();
                dateOfEndOfPermission.setYear(dateOfEndOfPermission.getYear() + 1);
                if(date.get().compareTo(dateOfEndOfPermission) < 0){
                    date.set(dateOfEndOfPermission);
                }
                flagOfAccept.set(true);
            }

        });
        if(officeApplications.isEmpty()){
            return true;
        }
        if(flagOfAccept.get()){
            //check date of last permission
            if(date.get().compareTo(new Date()) <= 0 ){
                return true;
            }
            if(date.get().compareTo(new Date()) > 0 ){
                return false;
            }
        }
        return !flagOfWait.get();
    }
    public ResponseEntity get_office(){
        Iterable<OfficeModel> offices = officeRepo.findAll();
        offices.forEach(office->{
            Optional<ApplicationModel> lastApplication = applicationRepo.findById(office.getLastApplicationId());
            office.setStatusOfLastApplication(lastApplication.get().getStatus());
            officeRepo.save(office);
        });

        return ResponseEntity.ok(offices);

    }


    public ResponseEntity get_office_by_name (String name ){
        OfficeModel office = officeRepo.findByName(name);
        if(office == null){
            return ResponseEntity.status(400).body(new Message("Can not find office"));
        }

        Set<ApplicationModel> officeApplications = applicationRepo.findAllByOffice(office);

        if(!checkOfficeApplications(officeApplications)){
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
