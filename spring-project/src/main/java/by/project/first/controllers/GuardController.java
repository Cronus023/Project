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
public class GuardController {

    private final GuardService guardService;

    @Autowired
    public GuardController(GuardService guardService) {
        this.guardService = guardService;
    }

    @GetMapping("/guards/checkProviderOffice")
    public ResponseEntity<Message> checkProviderOffice(@RequestParam String login, @RequestParam String officeName) {
        return guardService.checkProviderOffice(login, officeName);
    }

    @GetMapping("/guards/checkWorkerId")
    public ResponseEntity<Message> checkWorkerId(@RequestParam Long id) {
        return guardService.checkWorkerId(id);
    }

    @GetMapping("/guards/checkTrainingControl")
    public ResponseEntity<Message> checkTrainingControl(@RequestParam Long id, @RequestParam String login) {
        return guardService.checkTrainingControl(id, login);
    }

    @GetMapping("/guards/checkApplication")
    public ResponseEntity<Message> checkApplication(@RequestParam Long id) {
        return guardService.checkApplication(id);
    }

}
