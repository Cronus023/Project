package by.project.first.service;


import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity get_offices_by_login (String login){
        UserModel provider = userRepo.findByLogin(login);
        if(provider == null){
            return ResponseEntity.status(400).body(new Message("Wrong provider login!"));
        }
        Iterable<OfficeModel> offices = officeRepo.findAll();
        Set<OfficeModel> providerOffices = new HashSet<>();

        offices.forEach(office->{
            if(office.getLeaderID().contains(provider)){
                providerOffices.add(office);
            }
        });
        return ResponseEntity.ok(providerOffices);
    }
}
