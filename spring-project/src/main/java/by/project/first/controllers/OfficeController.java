package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.models.OfficeModel;
import by.project.first.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody OfficeModel office){
        return officeService.create_office(office);
    }

    @GetMapping("/get_office")
    public ResponseEntity main(){
        return officeService.get_office();
    }

    @GetMapping("/get_office_by_login")
    public ResponseEntity get_office_by_login (@RequestParam String login){
        return officeService.get_offices_by_login(login);
    }

    @GetMapping("/get_office_by_name")
    public ResponseEntity get_office_by_name (@RequestParam String name){
        return officeService.get_office_by_name(name);
    }

    @PostMapping("/become")
    public ResponseEntity become(@RequestBody BecomeRequest request){
        return officeService.become_provider(request);
    }
}