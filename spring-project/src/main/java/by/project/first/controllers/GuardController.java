package by.project.first.controllers;

import by.project.first.models.Message;
import by.project.first.service.GuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class GuardController {

    @Autowired
    private GuardService guardService;

    @GetMapping("/guards/check_provider_office")
    public ResponseEntity<Message> check_provider_office(@RequestParam String login, @RequestParam String officeName) {
        return guardService.check_provider_office(login, officeName);
    }

    @GetMapping("/guards/check_worker_id")
    public ResponseEntity<Message> check_worker_id(@RequestParam Long id) {
        return guardService.check_worker_id(id);
    }

    @GetMapping("/guards/check_training_control")
    public ResponseEntity<Message> check_training_control(@RequestParam Long id, @RequestParam String login) {
        return guardService.check_training_control(id, login);
    }

    @GetMapping("/guards/check_application")
    public ResponseEntity<Message> check_application(@RequestParam Long id) {
        return guardService.check_application(id);
    }
}
