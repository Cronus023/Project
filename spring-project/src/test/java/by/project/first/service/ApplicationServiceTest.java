package by.project.first.service;

import by.project.first.controllers.ReqAndRes.ApplicationCreateRequest;
import by.project.first.controllers.ReqAndRes.RegularReviewerResponse;
import by.project.first.controllers.ReqAndRes.RejectAndAcceptRequest;
import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private ResponseToApplicationRepo responseToApplicationRepo;


    @Test
    public void create_application() {
        ApplicationModel testApplication = new ApplicationModel();
        String officeName = "testOffice";
        OfficeModel testOffice = new OfficeModel(officeName);
        officeRepo.save(testOffice);

        applicationService.create_application(new ApplicationCreateRequest(testApplication, testOffice));

        OfficeModel officeAfterUpdate = officeRepo.findByName(officeName);


        ApplicationModel applicationAfterSave = applicationRepo.findByOfficeName(officeName);


        assertEquals("WAIT_FOR_AN_ANSWER", applicationAfterSave.getStatus());
        assertEquals(applicationAfterSave, officeAfterUpdate.getLastApplication());
        assertNotNull(applicationAfterSave);
        assertNotNull(officeAfterUpdate.getLastApplication());

        officeRepo.deleteById(officeAfterUpdate.getId());
        applicationRepo.deleteById(applicationAfterSave.getId());
    }

    @Test
    public void get_applications() {
        ResponseEntity<Set<ApplicationModel>> response = applicationService.get_applications();
        Set<ApplicationModel> notAnsweredApplications = response.getBody();

        if(notAnsweredApplications != null){
            boolean check = false;
            for(ApplicationModel application : notAnsweredApplications){
                if(!application.getStatus().equals("WAIT_FOR_AN_ANSWER")){
                    check = true;
                    break;
                }
            }
            assertFalse(check);
            System.out.println(notAnsweredApplications);
        }
    }

    @Test
    public void get_responses_and_application() {
        ApplicationModel testApplication = new ApplicationModel();
        applicationRepo.save(testApplication);

        UserModel testUser = new UserModel("testUser", "testUser");
        userRepo.save(testUser);

        ResponseToApplicationModel testResponse = new ResponseToApplicationModel();
        testResponse.setApplicationID(testApplication);
        testResponse.setUser(testUser);

        responseToApplicationRepo.save(testResponse);



        RegularReviewerResponse response = applicationService.get_responses_and_application("testUser", testApplication.getId());

        boolean check = false;
        for(ResponseToApplicationModel res : response.getResponses()){
            if(!res.equals(testResponse)){
                check = true;
                break;
            }
        }
        assertFalse(check);
        assertEquals(response.getApplication(), testApplication);


        responseToApplicationRepo.deleteById(testResponse.getId());
        userRepo.deleteById(testUser.getId());
        applicationRepo.deleteById(testApplication.getId());
    }

    @Test
    public void reject_accept() {
        ApplicationModel testApplication = new ApplicationModel();
        applicationRepo.save(testApplication);

        UserModel testUser = new UserModel("testUser", "testUser");
        userRepo.save(testUser);


        applicationService.reject_accept(new RejectAndAcceptRequest("testUser", testApplication, "REJECT", "CURRICULUM"));

        Iterable<ResponseToApplicationModel> responses = responseToApplicationRepo.findAllByUser(testUser);

        ResponseToApplicationModel newResponse = null;
        for(ResponseToApplicationModel response: responses){
            newResponse = response;
            break;
        }

        assertNotNull(newResponse);
        assertEquals("REJECT", newResponse.getResponseStatus());
        assertEquals("CURRICULUM", newResponse.getTypeOfSection());
        assertEquals(testApplication, newResponse.getApplicationID());


        responseToApplicationRepo.deleteById(newResponse.getId());
        userRepo.deleteById(testUser.getId());
        applicationRepo.deleteById(testApplication.getId());
    }

    @Test
    public void get_history() {
        Set<ResponseToApplicationModel> testResponses = new HashSet<>();
        ApplicationModel testApplication = new ApplicationModel();
        applicationRepo.save(testApplication);

        ResponseToApplicationModel testResponse = new ResponseToApplicationModel();
        testResponse.setApplicationID(testApplication);
        responseToApplicationRepo.save(testResponse);

        ResponseToApplicationModel testResponse1 = new ResponseToApplicationModel();
        testResponse1.setApplicationID(testApplication);
        responseToApplicationRepo.save(testResponse1);

        testResponses.add(testResponse1);
        testResponses.add(testResponse);

        ResponseEntity<Iterable<ResponseToApplicationModel>> response = applicationService.get_history(testApplication.getId());


        assertNotNull(response.getBody());

        boolean checkResponses = false;
        for(ResponseToApplicationModel res: response.getBody()){
            boolean checkResponse = false;
            for(ResponseToApplicationModel testRes: testResponses){
                if(res.equals(testRes)){
                    checkResponse = true;
                    break;
                }
            }
            if(!checkResponse){
                checkResponses = true;
                break;
            }
        }

        assertFalse(checkResponses);

        responseToApplicationRepo.deleteById(testResponse.getId());
        responseToApplicationRepo.deleteById(testResponse1.getId());
        applicationRepo.deleteById(testApplication.getId());

    }

    @Test
    public void get_provider_applications() {
        ApplicationModel testApplication = new ApplicationModel();
        ApplicationModel testApplication1 = new ApplicationModel();

        UserModel testUser = new UserModel("testUser", "testUser");
        userRepo.save(testUser);

        String officeName = "testOffice";
        OfficeModel testOffice = new OfficeModel(officeName);
        testOffice.getLeaderID().add(testUser);
        officeRepo.save(testOffice);

        applicationService.create_application(new ApplicationCreateRequest(testApplication, testOffice));
        applicationService.create_application(new ApplicationCreateRequest(testApplication1, testOffice));

        OfficeModel officeAfterUpdate = officeRepo.findByName(officeName);

        Set<ApplicationModel> testApplications = applicationRepo.findAllByOfficeName(officeName);
        ResponseEntity<Set<ApplicationModel>> response = applicationService.get_provider_applications("testUser");

        assertNotNull(response.getBody());

        boolean checkResponses = false;
        for(ApplicationModel application: response.getBody()){
            boolean checkResponse = false;
            for(ApplicationModel test: testApplications){
                if(application.equals(test)){
                    checkResponse = true;
                    break;
                }
            }
            if(!checkResponse){
                checkResponses = true;
                break;
            }
        }
        assertFalse(checkResponses);


        officeRepo.deleteById(officeAfterUpdate.getId());
        response.getBody().forEach(res->{
            applicationRepo.deleteById(res.getId());
        });
        userRepo.deleteById(testUser.getId());
    }

    @Test
    public void final_decisionREJECT() {
        ApplicationModel testApplication = new ApplicationModel();

        String officeName = "testOffice";

        OfficeModel testOffice = new OfficeModel(officeName);
        officeRepo.save(testOffice);

        applicationService.create_application(new ApplicationCreateRequest(testApplication, testOffice));

        OfficeModel officeAfterUpdate = officeRepo.findByName(officeName);
        ApplicationModel applicationAfterSave = applicationRepo.findByOfficeName(officeName);

        applicationService.final_decision(applicationAfterSave.getId(), "REJECT");
        ApplicationModel applicationAfterFinalDecision = applicationRepo.findByOfficeName(officeName);

        assertEquals(applicationAfterFinalDecision.getStatus(), "REJECT");

        officeRepo.deleteById(officeAfterUpdate.getId());
        applicationRepo.deleteById(applicationAfterFinalDecision.getId());
    }
    @Test
    public void final_decisionACCEPT() {
        ApplicationModel testApplication = new ApplicationModel();

        String officeName = "testOffice";

        OfficeModel testOffice = new OfficeModel(officeName);
        officeRepo.save(testOffice);

        applicationService.create_application(new ApplicationCreateRequest(testApplication, testOffice));

        OfficeModel officeAfterUpdate = officeRepo.findByName(officeName);
        ApplicationModel applicationAfterSave = applicationRepo.findByOfficeName(officeName);

        applicationService.final_decision(applicationAfterSave.getId(), "ACCEPT");
        ApplicationModel applicationAfterFinalDecision = applicationRepo.findByOfficeName(officeName);
        OfficeModel officeAfterFinalDecision = officeRepo.findByName(officeName);

        assertEquals(1, new Date().compareTo(officeAfterFinalDecision.getDateOfLastPermission()));
        assertEquals(applicationAfterFinalDecision.getStatus(), "ACCEPT");

        officeRepo.deleteById(officeAfterUpdate.getId());
        applicationRepo.deleteById(applicationAfterFinalDecision.getId());
    }

}