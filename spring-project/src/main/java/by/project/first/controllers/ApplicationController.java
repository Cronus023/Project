package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.RejectAndAcceptRequest;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/application/create")
    public ResponseEntity create_application(@RequestBody ApplicationModel request){
        return applicationService.create_application(request);
    }

    @GetMapping("/application/get_applications")
    public ResponseEntity get_applications (){
        return applicationService.get_applications();
    }

    @GetMapping("/application/get_educational_program_by_id")
    public ResponseEntity get_educational_program (@RequestParam Long id, @RequestParam String login){
        return applicationService.get_educational_program(id, login);
    }

    @GetMapping("/application/get_application")
    public ResponseEntity get_application (@RequestParam Long id, @RequestParam String login){
        return applicationService.get_application(id, login);
    }

    @PostMapping("/application/reject_accept")
    public ResponseEntity reject_accept(@RequestBody RejectAndAcceptRequest request){
        return applicationService.reject_accept(request);
    }

}
