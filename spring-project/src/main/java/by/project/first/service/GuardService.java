package by.project.first.service;


import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GuardService {
    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity check_provider_office (String login, String officeName){
        OfficeModel office = officeRepo.findByName(officeName);
        if(office == null){
            return ResponseEntity.status(400).body(new Message("badStatus1"));
        }

        UserModel provider = userRepo.findByLogin(login);
        if(provider == null){
            return ResponseEntity.status(400).body(new Message("badStatus2"));
        }

        Set<UserModel> officeProviders = office.getLeaderID();
        if(officeProviders.contains(provider)){
            return  ResponseEntity.ok(new Message("ok!"));
        }
        return  ResponseEntity.ok(new Message("bad!"));
    }
}
