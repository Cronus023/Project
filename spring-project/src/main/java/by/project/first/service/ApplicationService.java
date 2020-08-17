package by.project.first.service;

import by.project.first.controllers.ReqAndRes.RegularReviewerResponse;
import by.project.first.controllers.ReqAndRes.RejectAndAcceptRequest;
import by.project.first.models.ApplicationModels.*;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ApplicationService {

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private ResponseToApplicationRepo responseToApplicationRepo;

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private ReasonRepo reasonRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    public ResponseEntity create_application(ApplicationModel request){
        OfficeModel office = officeRepo.findByName(request.getOffice().getName());

        if(office == null){
            ResponseEntity.status(400).body(new Message("Can not find office"));
        }
        else{
            Set<ReasonsModel> reasons = new HashSet<>();
            Set<GroupsModel> groups = new HashSet<>();

            request.getReasons().forEach(reason->{
                reasons.add(reasonRepo.save(reason));
            });
            request.getGroups().forEach(group->{
                groups.add(groupRepo.save(group));
            });
            office.setLocation(request.getOffice().getLocation());
            OfficeModel newOffice = officeRepo.save(office);

            ApplicationModel application = new ApplicationModel(reasons, groups, newOffice, request.getEducationalProgram(),request.getAdditionalInfo());
            ApplicationModel newApplication = applicationRepo.save(application);

            return ResponseEntity.ok(newApplication);
        }
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity get_applications(){
        Iterable<ApplicationModel> applications = applicationRepo.findAll();
        return ResponseEntity.ok(applications);
    }

    public ResponseEntity get_educational_program(Long id){
        Optional<ApplicationModel> application = applicationRepo.findById(id);
        if(application.isEmpty()) {
           return ResponseEntity.status(400).body(new Message("Can not find application"));
        }
        return ResponseEntity.ok(application);
    }

    public ResponseEntity get_application(Long id, String login){
        UserModel user = userRepo.findByLogin(login);
        if(user == null){
            return ResponseEntity.status(400).body(new Message("Can not find user by login"));
        }

        Optional<ApplicationModel> application = applicationRepo.findById(id);
        if(application.isEmpty()) {
            return ResponseEntity.status(400).body(new Message("Can not find application"));
        }
        Iterable<ResponseToApplicationModel> responsesOfCurrentUser = responseToApplicationRepo.findAllByUser(user);

        Set<WorkerModelForResponse> workers = new HashSet<>();
        application.get().getReasons().forEach(reason->{
            Optional<WorkerModel> worker = workerRepo.findById(reason.getWorkerID());
            workers.add(new WorkerModelForResponse(worker.get(), reason.getReason() ));
        });

        return ResponseEntity.ok(new RegularReviewerResponse(workers, application.get(), responsesOfCurrentUser));

    }

    public ResponseEntity reject_accept(RejectAndAcceptRequest request){
        UserModel user = userRepo.findByLogin(request.getLogin());
        if(user == null){
            return ResponseEntity.status(400).body(new Message("Can not find user by login"));
        }
        Date date = new Date();
        ResponseToApplicationModel response = new ResponseToApplicationModel(request.getStatus(), request.getTypeOfSection(), user, request.getApplication(), date);
        return ResponseEntity.ok(responseToApplicationRepo.save(response));
    }
}


