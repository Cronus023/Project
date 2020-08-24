package by.project.first.service;

import by.project.first.controllers.ReqAndRes.ApplicationCreateRequest;
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

    public ResponseEntity<Message> create_application(ApplicationCreateRequest request){
        OfficeModel office = officeRepo.findByName(request.getOffice().getName());

        if(office == null){
            ResponseEntity.status(400).body(new Message("Can not find office"));
        }
        else{
            Set<ReasonsModel> reasons = new HashSet<>();
            Set<GroupsModel> groups = new HashSet<>();
            reasonRepo.saveAll(request.getApplication().getReasons());
            groupRepo.saveAll(request.getApplication().getGroups());

            ApplicationModel application = new ApplicationModel(reasons, groups, request.getApplication().getEducationalProgram(),request.getApplication().getAdditionalInfo(), office.getName(), office.getLocation());
            ApplicationModel newApplication = applicationRepo.save(application);

            office.setLastApplication(newApplication);
            office.getOfficeApplications().add(newApplication);

            officeRepo.save(office);
            return ResponseEntity.ok(new Message("ok!"));
        }
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Set<ApplicationModel>> get_applications(){
        Iterable<ApplicationModel> applications = applicationRepo.findAll();
        Set<ApplicationModel> notAnsweredApplications = new HashSet<>();
        applications.forEach(application->{
            if(application.getStatus().equals("WAIT_FOR_AN_ANSWER")){
                notAnsweredApplications.add(application);
            }
        });
        return ResponseEntity.ok(notAnsweredApplications);
    }

    public ResponseEntity get_educational_program(Long id, String login){
        RegularReviewerResponse response = get_responses_and_application(login, id);
        return ResponseEntity.ok(response);
    }

    public RegularReviewerResponse get_responses_and_application (String login, Long id){
        UserModel user = userRepo.findByLogin(login);
        Optional<ApplicationModel> application = applicationRepo.findById(id);
        Iterable<ResponseToApplicationModel> responsesOfCurrentUser = responseToApplicationRepo.findAllByUser(user);
        return new RegularReviewerResponse(application.get(), responsesOfCurrentUser);
    }

    public ResponseEntity<RegularReviewerResponse> get_application(Long id, String login){
        RegularReviewerResponse response = get_responses_and_application(login, id);
        Set<WorkerModelForResponse> workers = new HashSet<>();
        response.getApplication().getReasons().forEach(reason->{
            Optional<WorkerModel> worker = workerRepo.findById(reason.getWorkerID());
            workers.add(new WorkerModelForResponse(worker.get(), reason.getReason() ));
        });
        return ResponseEntity.ok(new RegularReviewerResponse(workers, response.getApplication(), response.getResponses()));
    }

    public ResponseEntity<Message> reject_accept(RejectAndAcceptRequest request){
        UserModel user = userRepo.findByLogin(request.getLogin());
        Date date = new Date();
        ResponseToApplicationModel response = new ResponseToApplicationModel(request.getStatus(), request.getTypeOfSection(), user, request.getApplication(), date);
        responseToApplicationRepo.save(response);
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Iterable<ResponseToApplicationModel>> get_history(Long id){
        Optional<ApplicationModel> application = applicationRepo.findById(id);
        Iterable<ResponseToApplicationModel> responsesOfApplication = responseToApplicationRepo.findAllByApplicationID(application.get());
        return ResponseEntity.ok(responsesOfApplication);
    }

    public ResponseEntity<Set<ApplicationModel>> get_provider_applications(String login){
        UserModel user = userRepo.findByLogin(login);
        Set<ApplicationModel> providerApplications = new HashSet<>();
        Iterable<OfficeModel> providerOffices = officeRepo.findAllByLeaderID(user);

        providerOffices.forEach(office->{
            Set<ApplicationModel> officeApplications = office.getOfficeApplications();
            providerApplications.addAll(officeApplications);
        });

        return ResponseEntity.ok(providerApplications);
    }

    public ResponseEntity final_decision(Long id, String decision){
        Optional<ApplicationModel> application = applicationRepo.findById(id);
        OfficeModel office = officeRepo.findByLastApplication(application);

        if(office == null){
            return ResponseEntity.status(400).body(new Message("Can not find office!"));
        }

        if(decision.equals("ACCEPT")){
            office.setDateOfLastPermission(new Date());
            officeRepo.save(office);

            application.get().setStatus(decision);
            applicationRepo.save(application.get());
            return ResponseEntity.ok(new Message("ok!"));
        }
        if(decision.equals("REJECT")){
            application.get().setStatus(decision);
            applicationRepo.save(application.get());
            return ResponseEntity.ok(new Message("ok!"));
        }
        return ResponseEntity.status(400).body(new Message("Something wrong"));
    }
}


