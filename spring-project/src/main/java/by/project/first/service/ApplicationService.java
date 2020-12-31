package by.project.first.service;

import by.project.first.controllers.ReqAndRes.ApplicationCreateRequest;
import by.project.first.controllers.ReqAndRes.RegularReviewerResponse;
import by.project.first.controllers.ReqAndRes.RejectAndAcceptRequest;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.GroupsModel;
import by.project.first.models.ApplicationModels.ReasonsModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.ApplicationModels.WorkerModelForResponse;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.ApplicationRepo;
import by.project.first.repositories.GroupRepo;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.ReasonRepo;
import by.project.first.repositories.ResponseToApplicationRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ApplicationService {

    private final OfficeRepo officeRepo;
    private final ApplicationRepo applicationRepo;
    private final ResponseToApplicationRepo responseToApplicationRepo;
    private final WorkerRepo workerRepo;
    private final ReasonRepo reasonRepo;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;

    @Autowired
    public ApplicationService(OfficeRepo officeRepo, ApplicationRepo applicationRepo, WorkerRepo workerRepo,
                              ReasonRepo reasonRepo, UserRepo userRepo, GroupRepo groupRepo,
                              ResponseToApplicationRepo responseToApplicationRepo) {
        this.officeRepo = officeRepo;
        this.applicationRepo = applicationRepo;
        this.workerRepo = workerRepo;
        this.reasonRepo = reasonRepo;
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
        this.responseToApplicationRepo = responseToApplicationRepo;
    }

    public Message createApplication(ApplicationCreateRequest request) {
        OfficeModel office = officeRepo.findByName(request.getOffice().getName());
        Set<ReasonsModel> reasons = request.getApplication().getReasons();
        Set<GroupsModel> groups = request.getApplication().getGroups();

        reasonRepo.saveAll(reasons);
        groupRepo.saveAll(groups);
        saveOfficeAndApplication(office, reasons, groups,
                request.getApplication().getAdditionalInfo(), request.getApplication().getEducationalProgram());

        return new Message("ok!");
    }

    public void saveOfficeAndApplication(OfficeModel office, Set<ReasonsModel> reasons, Set<GroupsModel> groups,
                                         String additionalInfo, String educationalProgram) {
        ApplicationModel newApplication = applicationRepo.save(new ApplicationModel(reasons, groups,
                educationalProgram, additionalInfo, office.getName(), office.getLocation())
        );

        office.setLastApplication(newApplication);
        office.getOfficeApplications().add(newApplication);
        officeRepo.save(office);
    }

    public Set<ApplicationModel> getApplications() {
        Set<ApplicationModel> notAnsweredApplications = new HashSet<>();
        applicationRepo.findAll().forEach(application -> {
            if (application.getStatus().equals("WAIT_FOR_AN_ANSWER")) {
                notAnsweredApplications.add(application);
            }
        });
        return notAnsweredApplications;
    }

    public RegularReviewerResponse getEducationalProgram(Long id, String login) {
        return getResponsesAndApplication(login, id);
    }

    public RegularReviewerResponse getResponsesAndApplication(String login, Long id) {
        UserModel user = userRepo.findByLogin(login);
        return new RegularReviewerResponse(applicationRepo.findById(id).get(),
                responseToApplicationRepo.findAllByUser(user)
        );
    }

    public RegularReviewerResponse getApplication(Long id, String login) {
        RegularReviewerResponse response = getResponsesAndApplication(login, id);
        Set<WorkerModelForResponse> workers = new HashSet<>();
        response.getApplication().getReasons().forEach(reason ->
                workers.add(new WorkerModelForResponse(
                        workerRepo.findById(reason.getWorkerID()).get(), reason.getReason()))
        );
        return new RegularReviewerResponse(workers, response.getApplication(), response.getResponses());
    }

    public Message rejectAndAccept(RejectAndAcceptRequest request) {
        UserModel user = userRepo.findByLogin(request.getLogin());
        ResponseToApplicationModel response = new ResponseToApplicationModel(request.getStatus(),
                request.getTypeOfSection(), user, request.getApplication(), new Date()
        );
        responseToApplicationRepo.save(response);
        return new Message("ok!");
    }

    public Iterable<ResponseToApplicationModel> getHistory(Long id) {
        return responseToApplicationRepo.findAllByApplicationID(applicationRepo.findById(id).get());
    }

    public Set<ApplicationModel> getProviderApplications(String login) {
        Set<ApplicationModel> providerApplications = new HashSet<>();
        officeRepo.findAllByLeaderID(userRepo.findByLogin(login)).forEach(office ->
                providerApplications.addAll(office.getOfficeApplications())
        );
        return providerApplications;
    }

    public ResponseEntity<Message> finalDecision(Long id, String decision) {
        Optional<ApplicationModel> application = applicationRepo.findById(id);
        OfficeModel office = officeRepo.findByLastApplication(application.get());

        if (office == null) return ResponseEntity.status(400).body(new Message("Can not find office!"));
        if (decision.equals("ACCEPT")) return finalDecisionAccept(office, application.get(), decision);
        if (decision.equals("REJECT")) return finalDecisionReject(application.get(), decision);

        return ResponseEntity.status(400).body(new Message("Something wrong"));
    }

    public ResponseEntity<Message> finalDecisionAccept(OfficeModel office,
                                                       ApplicationModel application, String decision) {
        office.setDateOfLastPermission(new Date());
        officeRepo.save(office);
        application.setStatus(decision);
        applicationRepo.save(application);
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Message> finalDecisionReject(ApplicationModel application, String decision) {
        application.setStatus(decision);
        applicationRepo.save(application);
        return ResponseEntity.ok(new Message("ok!"));
    }

}


