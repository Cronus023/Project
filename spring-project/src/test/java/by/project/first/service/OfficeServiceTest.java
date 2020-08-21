package by.project.first.service;

import by.project.first.controllers.ReqAndRes.FindNotPassedWorkersResponse;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class OfficeServiceTest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeRepo officeRepo;


    @Test
    void become_provider() {
        String login = "root4";
        UserModel provider = userRepo.findByLogin(login);
        //officeService.become_provider(new BecomeRequest(officeRepo.findByName("Itransitionded"), login));
        Set<OfficeModel> providerOffices = officeRepo.findAllByLeaderID(provider);
        assertNotNull(providerOffices);

        OfficeModel providerOffice = officeRepo.findByName("Itransitionded");

        boolean result = false;
        for(OfficeModel office: providerOffices){
            result = office.equals(providerOffice);
            if(result){
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    void create_office() {
        OfficeModel officeBad = officeRepo.findByName("Itransitionded");
        ResponseEntity<Message> responseBad= officeService.create_office(officeBad);
        Assertions.assertEquals(400, responseBad.getStatusCode().value());

        OfficeModel officeOk = new OfficeModel("TestOffice");
        ResponseEntity<Message> responseOk= officeService.create_office(officeOk);
        Assertions.assertEquals(200, responseOk.getStatusCode().value());

        OfficeModel officeAfterCreate = officeRepo.findByName("TestOffice");
        assertNotNull(officeAfterCreate);
        officeRepo.delete(officeAfterCreate);
    }

    @Test
    void checkOfficeApplications() {
        OfficeModel testOfficeWait = officeRepo.findByName("Itransitionded");

        ApplicationModel lastApplicationWait = testOfficeWait.getLastApplication();
        boolean checkWait = officeService.checkOfficeApplications(lastApplicationWait);
        assertFalse(checkWait);

        OfficeModel testOfficeAccept = officeRepo.findByName("Itransition");
        ApplicationModel lastApplicationAccept = testOfficeAccept.getLastApplication();
        boolean checkAccept = officeService.checkOfficeApplications(lastApplicationAccept);
        assertFalse(checkAccept);

        ApplicationModel lastApplicationNull = new ApplicationModel();
        boolean checkNull = officeService.checkOfficeApplications(lastApplicationNull);
        assertFalse(checkNull);

        lastApplicationNull.setStatus("REJECT");
        boolean checkReject = officeService.checkOfficeApplications(lastApplicationNull);
        assertTrue(checkReject);
    }


    @Test
    void get_office_by_name() {
        //check office with application
        OfficeModel testOfficeForBad = officeRepo.findByName("Itransitionded");
        ResponseEntity responseBad = officeService.get_office_by_name(testOfficeForBad.getName());
        assertEquals(400, responseBad.getStatusCode().value());

        //check office without  application
        OfficeModel testOfficeForOk = officeRepo.findByName("Test");
        ResponseEntity<FindNotPassedWorkersResponse> responseOk = officeService.get_office_by_name(testOfficeForOk.getName());
        assertEquals(testOfficeForOk, responseOk.getBody().getOffice());

        //all workers in test office are not passed
        assertTrue(testOfficeForOk.equalsWorkers(responseOk.getBody().getNotPassedWorkers()));
    }

}