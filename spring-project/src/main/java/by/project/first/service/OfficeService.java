package by.project.first.service;


import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.controllers.ReqAndRes.FindNotPassedWorkersResponse;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class OfficeService {
    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private UserRepo userRepo;

    public Iterable<OfficeModel> getOfficesByLogin(String login) {
        return officeRepo.findAllByLeaderID(userRepo.findByLogin(login));
    }

    public Message becomeProvider(BecomeRequest request) {
        UserModel user = userRepo.findByLogin(request.getLogin());
        OfficeModel office = request.getOffice();
        office.getLeaderID().add(user);
        officeRepo.save(office);
        return new Message("ok!");
    }

    public ResponseEntity<Message> createOffice(OfficeModel office) {
        OfficeModel newOffice = officeRepo.findByName(office.getName());
        if (newOffice == null) {
            officeRepo.save(office);
            return ResponseEntity.ok(new Message(""));
        } else return ResponseEntity.status(400).body(new Message("Such office already exist!"));
    }

    public boolean checkOfficeApplications(ApplicationModel lastApplication) {
        if (lastApplication == null) return true;
        if (lastApplication.getStatus().equals("WAIT_FOR_AN_ANSWER")) return false;
        if (lastApplication.getStatus().equals("REJECT")) return true;

        if (lastApplication.getStatus().equals("ACCEPT")) {
            return checkStatusAccept(lastApplication);
        }
        return false;
    }

    public boolean checkStatusAccept(ApplicationModel lastApplication) {
        Date dateOfEndOfPermission = lastApplication.getDateOfApplication();
        dateOfEndOfPermission.setYear(dateOfEndOfPermission.getYear() + 1);
        return dateOfEndOfPermission.compareTo(new Date()) <= 0;
    }

    public Iterable<OfficeModel> getOffices() {
        return officeRepo.findAll();
    }

    public ResponseEntity getOfficeByName(String name) {
        OfficeModel office = officeRepo.findByName(name);
        ApplicationModel lastApplication = office.getLastApplication();

        if (!checkOfficeApplications(lastApplication))
            return ResponseEntity.status(400).body(new Message("Application already exist!"));

        return ResponseEntity.ok(new FindNotPassedWorkersResponse(office, getNotPassedWorkers(office.getWorkerId())));
    }

    public Set<WorkerModel> getNotPassedWorkers(Set<WorkerModel> passedWorkers) {
        Set<WorkerModel> notPassedWorkers = new HashSet<>();
        passedWorkers.forEach(worker -> {
            if (trainingRepo.findAllByTrainingPassedID(worker).isEmpty()) {
                notPassedWorkers.add(worker);
            }
        });
        return notPassedWorkers;
    }


}
