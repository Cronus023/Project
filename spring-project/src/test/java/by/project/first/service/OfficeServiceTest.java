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

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class OfficeServiceTest {

    @Autowired
    private OfficeService officeService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private OfficeRepo officeRepo;

    @MockBean
    private TrainingRepo trainingRepo;

    @Test
    void becomeProviderTest() {
        OfficeModel testOffice = new OfficeModel();

        UserModel testUser = new UserModel("testUser", "testUser");

        Mockito.doReturn(testUser)
                .when(userRepo)
                .findByLogin("testUser");

        officeService.becomeProvider(new BecomeRequest(testOffice, "testUser"));

        Mockito.verify(officeRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testOffice));

        boolean result = testOffice.getLeaderID().contains(testUser);
        assertTrue(result);
    }

    @Test
    void createOfficeTest() {
        OfficeModel testOffice = new OfficeModel();

        officeService.createOffice(testOffice);

        Mockito.verify(officeRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testOffice));
    }

    @Test
    void createOfficeFailTest() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        Mockito.doReturn(new OfficeModel())
                .when(officeRepo)
                .findByName("testOffice");

        ResponseEntity<Message> response = officeService.createOffice(testOffice);
        assertEquals(400, response.getStatusCode().value());

        Mockito.verify(officeRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(OfficeModel.class));
    }

    @Test
    void checkOfficeApplicationsNull() {
        OfficeModel testOffice = new OfficeModel();
        boolean checkNull = officeService.checkOfficeApplications(testOffice.getLastApplication());
        assertTrue(checkNull);
    }

    @Test
    void checkOfficeApplicationsWait() {
        ApplicationModel testApplication = new ApplicationModel();
        boolean checkWait = officeService.checkOfficeApplications(testApplication);
        assertFalse(checkWait);
    }

    @Test
    void checkOfficeApplicationsReject() {
        ApplicationModel testApplication = new ApplicationModel();
        testApplication.setStatus("REJECT");

        boolean checkReject = officeService.checkOfficeApplications(testApplication);
        assertTrue(checkReject);
    }

    @Test
    void checkOfficeApplicationsAcceptFalse() {
        ApplicationModel testApplication = new ApplicationModel();
        testApplication.setStatus("ACCEPT");
        testApplication.setDateOfApplication(new Date());

        boolean checkAccept = officeService.checkOfficeApplications(testApplication);
        assertFalse(checkAccept);
    }

    @Test
    void checkOfficeApplicationsAcceptTrue() {
        ApplicationModel testApplication = new ApplicationModel();
        testApplication.setStatus("ACCEPT");
        Date date = new Date();
        date.setYear(date.getYear() - 5);
        testApplication.setDateOfApplication(date);

        boolean checkAccept = officeService.checkOfficeApplications(testApplication);
        assertTrue(checkAccept);
    }

    @Test
    void getOfficeByName() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        WorkerModel testWorker2 = new WorkerModel("testWorker2");

        Set<WorkerModel> officeWorkers = new HashSet<>();
        officeWorkers.add(testWorker1);
        officeWorkers.add(testWorker2);

        testOffice.setWorkerId(officeWorkers);

        Mockito.doReturn(testOffice)
                .when(officeRepo)
                .findByName("testOffice");

        ResponseEntity<FindNotPassedWorkersResponse> response = officeService.getOfficeByName("testOffice");

        assertNotNull(response.getBody());
        assertEquals(response.getBody().getOffice().getName(), testOffice.getName());
        assertEquals(response.getBody().getNotPassedWorkers(), officeWorkers);

        Mockito.verify(trainingRepo, Mockito.times(2))
                .findAllByTrainingPassedID(ArgumentMatchers.any(WorkerModel.class));
    }

}