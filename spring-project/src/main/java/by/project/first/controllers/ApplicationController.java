package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.ApplicationCreateRequest;
import by.project.first.controllers.ReqAndRes.RegularReviewerResponse;
import by.project.first.controllers.ReqAndRes.RejectAndAcceptRequest;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.Message;
import by.project.first.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/application/createApplication")
    public Message createApplication(@RequestBody ApplicationCreateRequest request) {
        return applicationService.createApplication(request);
    }

    @GetMapping("/application/getApplications")
    public Set<ApplicationModel> getApplications() {
        return applicationService.getApplications();
    }

    @GetMapping("/application/getEducationalProgramById")
    public RegularReviewerResponse getEducationalProgram(@RequestParam Long id, @RequestParam String login) {
        return applicationService.getEducationalProgram(id, login);
    }

    @GetMapping("/application/getApplication")
    public RegularReviewerResponse getApplication(@RequestParam Long id, @RequestParam String login) {
        return applicationService.getApplication(id, login);
    }

    @GetMapping("/application/getHistory")
    public Iterable<ResponseToApplicationModel> getHistory(@RequestParam Long id) {
        return applicationService.getHistory(id);
    }

    @GetMapping("/application/getProviderApplications")
    public Set<ApplicationModel> getProviderApplications(@RequestParam String login) {
        return applicationService.getProviderApplications(login);
    }

    @PostMapping("/application/rejectAndAccept")
    public Message rejectAndAccept(@RequestBody RejectAndAcceptRequest request) {
        return applicationService.rejectAndAccept(request);
    }

    @PostMapping("/application/finalDecision")
    public ResponseEntity<Message> finalDecision(@RequestParam Long id, @RequestParam String decision) {
        return applicationService.finalDecision(id, decision);
    }

}
