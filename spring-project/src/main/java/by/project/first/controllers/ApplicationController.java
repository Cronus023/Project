package by.project.first.controllers;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/application/create")
    public ResponseEntity create_application(@RequestBody ApplicationModel request){
        return applicationService.create_application(request);
    }
}
