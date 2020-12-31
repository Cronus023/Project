package by.project.first.service;

import by.project.first.controllers.ReqAndRes.ApplicationCreateRequest;
import by.project.first.controllers.ReqAndRes.RejectAndAcceptRequest;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.ApplicationRepo;
import by.project.first.repositories.GroupRepo;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.ReasonRepo;
import by.project.first.repositories.ResponseToApplicationRepo;
import by.project.first.repositories.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private OfficeRepo officeRepo;

    @MockBean
    private ApplicationRepo applicationRepo;

    @MockBean
    private ReasonRepo reasonRepo;

    @MockBean
    private GroupRepo groupRepo;

    @MockBean
    private ResponseToApplicationRepo responseToApplicationRepo;

    @Test
    public void createApplicationTest() {
        ApplicationModel testApplication = new ApplicationModel();

        OfficeModel testOffice = new OfficeModel("testOffice");
        testApplication.setOfficeName("testOffice");

        createApplicationTestConfig(testOffice, "testOffice", testApplication);

        applicationService.createApplication(new ApplicationCreateRequest(testApplication, testOffice));

        assertEquals("WAIT_FOR_AN_ANSWER", testApplication.getStatus());
        assertEquals(testApplication, testOffice.getLastApplication());

        createApplicationTestVerify(testOffice, testApplication);
    }

    public void createApplicationTestConfig(OfficeModel testOffice,
                                            String officeName, ApplicationModel testApplication) {
        ApplicationModel app = new ApplicationModel(null, null,
                null, null, officeName, null);

        Mockito.doReturn(testOffice)
                .when(officeRepo)
                .findByName(officeName);

        Mockito.doReturn(testApplication)
                .when(applicationRepo)
                .save(app);
    }

    public void createApplicationTestVerify(OfficeModel testOffice, ApplicationModel testApplication) {
        Mockito.verify(reasonRepo, Mockito.times(1))
                .saveAll(ArgumentMatchers.eq(testApplication.getReasons()));

        Mockito.verify(groupRepo, Mockito.times(1))
                .saveAll(ArgumentMatchers.eq(testApplication.getGroups()));

        Mockito.verify(applicationRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testApplication));

        Mockito.verify(officeRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testOffice));
    }

    @Test
    public void getApplications() {
        ApplicationModel testApplication1 = new ApplicationModel();
        testApplication1.setStatus("WAIT_FOR_AN_ANSWER");

        ApplicationModel testApplication2 = new ApplicationModel();
        testApplication2.setStatus("WAIT_FOR_AN_ANSWER");

        ApplicationModel testApplication3 = new ApplicationModel();
        testApplication3.setStatus("ACCEPT");

        Set<ApplicationModel> applications = new HashSet<>();
        applications.add(testApplication1);
        applications.add(testApplication2);
        applications.add(testApplication3);

        Set<ApplicationModel> notAnsweredApplications = new HashSet<>();
        notAnsweredApplications.add(testApplication1);
        notAnsweredApplications.add(testApplication2);


        Mockito.doReturn(applications)
                .when(applicationRepo)
                .findAll();

        Set<ApplicationModel> notAnsweredApplications1 = applicationService.getApplications();
        assertEquals(notAnsweredApplications1, notAnsweredApplications);

    }

    @Test
    public void rejectAndAcceptTest() {
        ApplicationModel testApplication = new ApplicationModel();

        UserModel testUser = new UserModel("testUser", "testUser");

        Mockito.doReturn(testUser)
                .when(userRepo)
                .save(testUser);

        applicationService.rejectAndAccept(new RejectAndAcceptRequest(
                "testUser", testApplication, "REJECT", "CURRICULUM")
        );

        ResponseToApplicationModel response = new ResponseToApplicationModel("REJECT",
                "CURRICULUM", testUser, testApplication, new Date()
        );

        Mockito.verify(responseToApplicationRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(response));
    }

    @Test
    public void finalDecisionAcceptTest() {
        ApplicationModel testApplication = new ApplicationModel();
        OfficeModel testOffice = new OfficeModel();

        applicationService.finalDecisionAccept(testOffice, testApplication, "ACCEPT");

        assertEquals(testApplication.getStatus(), "ACCEPT");

        Mockito.verify(applicationRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testApplication));

        Mockito.verify(officeRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testOffice));
    }

    @Test
    public void finalDecisionRejectTest() {
        ApplicationModel testApplication = new ApplicationModel();

        applicationService.finalDecisionReject(testApplication, "REJECT");

        assertEquals(testApplication.getStatus(), "REJECT");

        Mockito.verify(applicationRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testApplication));
    }

    @Test
    public void finalDecisionFailTest() {
        ApplicationModel testApplication = new ApplicationModel();

        OfficeModel testOffice = new OfficeModel("testOffice");

        Mockito.doReturn(Optional.of(testApplication))
                .when(applicationRepo)
                .findById(10000L);

        Mockito.doReturn(null)
                .when(officeRepo)
                .findByLastApplication(testApplication);

        applicationService.finalDecision(10000L, "ACCEPT");

        assertEquals("WAIT_FOR_AN_ANSWER", testApplication.getStatus());

        Mockito.verify(applicationRepo, Mockito.times(0))
                .save(ArgumentMatchers.eq(testApplication));

        Mockito.verify(officeRepo, Mockito.times(0))
                .save(ArgumentMatchers.eq(testOffice));
    }

}
