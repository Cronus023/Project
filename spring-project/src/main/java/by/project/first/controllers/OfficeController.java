package by.project.first.controllers;

import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.OfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins="http://localhost:8000")
public class OfficeController {


    @Autowired
    private OfficeRepo officeRepo;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody OfficeModel office){

        OfficeModel newOffice = officeRepo.findByName(office.getName());
        if(newOffice == null){
            officeRepo.save(office);
            return ResponseEntity.ok(new Message(""));
        }
        else{
            return ResponseEntity.status(400).body(new Message("Such office already exist!"));
        }
    }
    @GetMapping("/get_office")
    public ResponseEntity main(){
        Iterable<OfficeModel> offices;
        offices = officeRepo.findAll();

        return ResponseEntity.ok(offices);
    }
}