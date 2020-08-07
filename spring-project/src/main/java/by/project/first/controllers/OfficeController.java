package by.project.first.controllers;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.Token;
import by.project.first.models.UserModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins="http://localhost:8000")
public class OfficeController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private UserRepo userRepo;

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
    @PostMapping("/become")
    public ResponseEntity become(@RequestBody BecomeRequest request){

        String login = jwtProvider.getLoginFromToken(request.getToken());
        if (login == null){
            return ResponseEntity.status(400).body(new Message("bad!"));
        }
        UserModel user = userRepo.findByLogin(login);

        OfficeModel office = request.getOffice();

        office.getLeaderID().add(user);
        officeRepo.save(office);
        return ResponseEntity.ok(new Message("Now you are provider of the office"));
    }
}