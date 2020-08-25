package by.project.first.service;

import by.project.first.controllers.ReqAndRes.BecomeRequest;
import by.project.first.controllers.ReqAndRes.FindNotPassedWorkersResponse;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.ApplicationRepo;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class OfficeServiceTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private WorkerRepo workerRepo;

    /*@Test
    void become_provider() {
        OfficeModel testOffice = new OfficeModel("testOffice");
        officeRepo.save(testOffice);

        UserModel testUser = new UserModel("testUser", "testUser");
        userRepo.save(testUser);

        officeService.become_provider(new BecomeRequest(testOffice, "testUser"));
        OfficeModel testOfficeAfterUpdate = officeRepo.findByName("testOffice");

        boolean result = false;
        for(UserModel user: testOfficeAfterUpdate.getLeaderID()){
            result = user.equals(testUser);
            if(result){
                break;
            }
        }
        assertTrue(result);

        officeRepo.deleteById(testOfficeAfterUpdate.getId());
        userRepo.deleteById(testUser.getId());
    }
    @Test
    void create_officeOK() {
        OfficeModel testOffice = new OfficeModel("testOffice");
        officeService.create_office(testOffice);
        OfficeModel officeAfterCreate = officeRepo.findByName("testOffice");
        assertNotNull(officeAfterCreate);

        officeRepo.deleteById(officeAfterCreate.getId());
    }

    @Test
    void create_officeBAD() {
        OfficeModel officeBad = officeRepo.findByName("Talisman");
        ResponseEntity<Message> responseBad= officeService.create_office(officeBad);
        Assertions.assertEquals(400, responseBad.getStatusCode().value());
    }

    @Test
    void checkOfficeApplicationsNull() {
        OfficeModel testOffice = new OfficeModel("testOffice");
        officeService.create_office(testOffice);

        ApplicationModel lastApplicationNull = testOffice.getLastApplication();
        boolean checkNull = officeService.checkOfficeApplications(lastApplicationNull);
        assertTrue(checkNull);

        officeRepo.deleteById(testOffice.getId());
    }

    @Test
    void checkOfficeApplicationsWait() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        ApplicationModel testApplication = new ApplicationModel();
        applicationRepo.save(testApplication);

        testOffice.setLastApplication(testApplication);
        officeService.create_office(testOffice);

        ApplicationModel lastApplicationWait = testOffice.getLastApplication();
        boolean checkWait = officeService.checkOfficeApplications(lastApplicationWait);
        assertFalse(checkWait);

        officeRepo.deleteById(testOffice.getId());
        applicationRepo.deleteById(testApplication.getId());
    }

    @Test
    void checkOfficeApplicationsReject() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        ApplicationModel testApplication = new ApplicationModel();
        testApplication.setStatus("REJECT");
        applicationRepo.save(testApplication);

        testOffice.setLastApplication(testApplication);
        officeService.create_office(testOffice);

        ApplicationModel lastApplicationReject = testOffice.getLastApplication();
        boolean checkReject = officeService.checkOfficeApplications(lastApplicationReject);
        assertTrue(checkReject);

        officeRepo.deleteById(testOffice.getId());
        applicationRepo.deleteById(testApplication.getId());
    }

    @Test
    void checkOfficeApplicationsAcceptFalse() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        ApplicationModel testApplication = new ApplicationModel();
        testApplication.setStatus("ACCEPT");
        testApplication.setDateOfApplication(new Date());
        applicationRepo.save(testApplication);

        testOffice.setLastApplication(testApplication);
        officeService.create_office(testOffice);

        ApplicationModel lastApplicationAcceptFalse = testOffice.getLastApplication();
        boolean checkAccept = officeService.checkOfficeApplications(lastApplicationAcceptFalse);
        assertFalse(checkAccept);

        officeRepo.deleteById(testOffice.getId());
        applicationRepo.deleteById(testApplication.getId());
    }

    @Test
    void checkOfficeApplicationsAcceptTrue() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        ApplicationModel testApplication = new ApplicationModel();
        testApplication.setStatus("ACCEPT");
        Date date = new Date();
        date.setYear(date.getYear() - 5);
        testApplication.setDateOfApplication(date);
        applicationRepo.save(testApplication);

        testOffice.setLastApplication(testApplication);
        officeService.create_office(testOffice);

        ApplicationModel lastApplicationAcceptTrue = testOffice.getLastApplication();
        boolean checkAccept = officeService.checkOfficeApplications(lastApplicationAcceptTrue);
        assertTrue(checkAccept);

        officeRepo.deleteById(testOffice.getId());
        applicationRepo.deleteById(testApplication.getId());
    }


    @Test
    void get_office_by_name() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        workerRepo.save(testWorker1);
        WorkerModel testWorker2 = new WorkerModel("testWorker2");
        workerRepo.save(testWorker2);

        Set<WorkerModel> officeWorkers = new HashSet<>();
        officeWorkers.add(testWorker1);
        officeWorkers.add(testWorker2);
        testOffice.setWorkerId(officeWorkers);

        officeService.create_office(testOffice);

        ResponseEntity<FindNotPassedWorkersResponse> response = officeService.get_office_by_name("testOffice");

        assertNotNull(response.getBody());
        assertEquals(response.getBody().getOffice(), testOffice);



        boolean checkWorkers = false;

        for(WorkerModel worker: officeWorkers){
            boolean checkWorker = false;
            for(WorkerModel notPassedWorker : response.getBody().getNotPassedWorkers()){
                if(notPassedWorker.equals(worker)){
                    checkWorker = true;
                    break;
                }
            }
            if(!checkWorker){
                checkWorkers = true;
                break;
            }
        }
        assertFalse(checkWorkers);

        officeRepo.deleteById(testOffice.getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }*/

}