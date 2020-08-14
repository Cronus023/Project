package by.project.first.service;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.GroupsModel;
import by.project.first.models.ApplicationModels.ReasonsModel;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.repositories.ApplicationRepo;
import by.project.first.repositories.GroupRepo;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.ReasonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationService {

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private ReasonRepo reasonRepo;

    @Autowired
    private GroupRepo groupRepo;

    public ResponseEntity create_application(ApplicationModel request){
        OfficeModel office = officeRepo.findByName(request.getOffice().getName());

        if(office == null){
            ResponseEntity.status(400).body(new Message("Can not find office"));
        }
        else{
            Set<ReasonsModel> reasons = new HashSet<>();
            Set<GroupsModel> groups = new HashSet<>();

            request.getReasons().forEach(reason->{
                reasons.add(reasonRepo.save(reason));
            });
            request.getGroups().forEach(group->{
                groups.add(groupRepo.save(group));
            });
            office.setLocation(request.getOffice().getLocation());
            OfficeModel newOffice = officeRepo.save(office);

            ApplicationModel application = new ApplicationModel(reasons, groups, newOffice, request.getEducationalProgram());
            ApplicationModel newApplication = applicationRepo.save(application);

            return ResponseEntity.ok(newApplication);
        }

        return ResponseEntity.ok(new Message("ok!"));
    }
}
