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
@CrossOrigin(origins="http://localhost:8000")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/application/create")
    public ResponseEntity<Message> create_application(@RequestBody ApplicationCreateRequest request){
        return applicationService.create_application(request);
    }

    @GetMapping("/application/get_applications")
    public ResponseEntity<Set<ApplicationModel>> get_applications (){
        return applicationService.get_applications();
    }

    @GetMapping("/application/get_educational_program_by_id")
    public ResponseEntity<RegularReviewerResponse> get_educational_program (@RequestParam Long id, @RequestParam String login){
        return applicationService.get_educational_program(id, login);
    }

    @GetMapping("/application/get_application")
    public ResponseEntity<RegularReviewerResponse> get_application (@RequestParam Long id, @RequestParam String login){
        return applicationService.get_application(id, login);
    }

    @GetMapping("/application/get_history")
    public ResponseEntity<Iterable<ResponseToApplicationModel>> get_history (@RequestParam Long id){
        return applicationService.get_history(id);
    }

    @GetMapping("/application/get_provider_applications")
    public ResponseEntity<Set<ApplicationModel>> get_provider_applications (@RequestParam String login){
        return applicationService.get_provider_applications(login);
    }

    @PostMapping("/application/reject_accept")
    public ResponseEntity<Message> reject_accept(@RequestBody RejectAndAcceptRequest request){
        return applicationService.reject_accept(request);
    }

    @PostMapping("/application/final_decision")
    public ResponseEntity<Message> make_final_decision(@RequestParam Long id, @RequestParam String decision){
        return applicationService.final_decision(id, decision);
    }
}
