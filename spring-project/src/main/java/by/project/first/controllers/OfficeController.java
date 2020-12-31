package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping("/createOffice")
    public ResponseEntity<Message> createOffice(@RequestBody OfficeModel office) {
        return officeService.createOffice(office);
    }

    @GetMapping("/getOffices")
    public Iterable<OfficeModel> getOffices() {
        return officeService.getOffices();
    }

    @GetMapping("/getOfficesByLogin")
    public Iterable<OfficeModel> getOfficesByLogin(@RequestParam String login) {
        return officeService.getOfficesByLogin(login);
    }

    @GetMapping("/getOfficeByName")
    public ResponseEntity getOfficeByName(@RequestParam String name) {
        return officeService.getOfficeByName(name);
    }

    @PostMapping("/becomeProvider")
    public Message becomeProvider(@RequestBody BecomeRequest request) {
        return officeService.becomeProvider(request);
    }

}