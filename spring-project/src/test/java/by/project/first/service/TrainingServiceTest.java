package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.FindTrainingWorkersResponse;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.*;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private WorkerService workerService;

    @Test
    void saveTraining() {

                                                      //TEST WORKING GOOD!!!!!!

        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        TrainingModel testTraining = new TrainingModel(testDate);

        UserModel testTrainer = userRepo.save(new UserModel("test", "test"));

        trainingService.saveTraining(new AddTrainingRequest(testTraining, testTrainer.getLogin()));

        TrainingModel training = trainingRepo.findByTrainerID(testTrainer);

        assertNotNull(training);
        assertEquals(testTrainer, training.getTrainerID());


        trainingRepo.delete(training);
        userRepo.delete(testTrainer);


    }

    @Test
    void registerWorkers() {
                                                 //TEST WORKING GOOD!!!!!!

        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        newWorkers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        newWorkers.add(testWorker2);


        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate, 10));

        ResponseEntity<Message> responseBadDate = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertEquals("Registration for this training is not possible", responseBadDate.getBody().getTitle());


        testDate.setYear(testDate.getYear() + 10);
        TrainingModel testTraining1 = trainingRepo.save( new TrainingModel(testDate, 0));
        ResponseEntity<Message> responseBadSeats = trainingService.registerWorkers(new RegWorkersToTraining(testTraining1.getId(), newWorkers));
        assertEquals("The number of seats for registration - 0!", responseBadSeats.getBody().getTitle());

        TrainingModel testTraining2 = trainingRepo.save( new TrainingModel(testDate, 1));
        ResponseEntity<Message> responseBadWorkers = trainingService.registerWorkers(new RegWorkersToTraining(testTraining2.getId(), newWorkers));
        assertEquals("Select fewer workers for registration!", responseBadWorkers.getBody().getTitle());


        trainingRepo.delete(testTraining);
        trainingRepo.delete(testTraining1);
        trainingRepo.delete(testTraining2);

        workerRepo.delete(testWorker1);
        workerRepo.delete(testWorker2);
    }

    @Test
    void edit_training() {
                                                  //TEST WORKING GOOD!!!!!!
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate));

        testDate.setYear(testDate.getYear() +10);
        testTraining.setDate(testDate);

        trainingService.edit_training(testTraining);
        Optional<TrainingModel> trainingAfterEdit = trainingRepo.findById(testTraining.getId());

        assertEquals(1, testDate.compareTo(trainingAfterEdit.get().getDate()));
        trainingRepo.delete(trainingAfterEdit.get());
    }

    @Test
    void addPassedWorkers() {
                           //TEST WORKING GOOD!!!!!!

        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);
        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate));

        Set<WorkerModel> passed = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        passed.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        passed.add(testWorker2);

        trainingService.addPassedWorkers(new RegWorkersToTraining(testTraining.getId(), passed));
        Optional<TrainingModel> trainingAfterAddPassed = trainingRepo.findById(testTraining.getId());

        assertTrue(trainingAfterAddPassed.get().equalsPassed(passed));

        trainingService.delete_training(testTraining.getId());
        workerRepo.delete(testWorker1);
        workerRepo.delete(testWorker2);
    }

    @Test
    void delete_training() {
                                                  //TEST WORKING GOOD!!!!!!

        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);
        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate));
        trainingService.delete_training(testTraining.getId());
        assertTrue(trainingRepo.findById(testTraining.getId()).isEmpty());
    }
    @Test
    void findTrainingWorkers() {
                                                                //TEST WORKING GOOD!!!!!!
        Date date = new Date();
        date.setYear(date.getYear() - 1);
        TrainingModel testTraining = new TrainingModel(date);

        Set<WorkerModel> workers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        workers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        workers.add(testWorker2);
        testTraining.getWorkerID().addAll(workers);

        trainingRepo.save(testTraining);

        ResponseEntity<FindTrainingWorkersResponse> response = trainingService.findTrainingWorkers(new GetWorkersTrainingRequest(testTraining.getId(), "trener"));
        assertTrue(testTraining.equalsWorkers(response.getBody().getTrainingWorkers()));

        trainingService.delete_training(testTraining.getId());
        workerRepo.delete(testWorker1);
        workerRepo.delete(testWorker2);
    }

    @Test
    void workersOfProvider() {
                                 //TEST WORKING GOOD!!!!!!

        OfficeModel office = new OfficeModel();
        Set<WorkerModel> workers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        workers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        workers.add(testWorker2);


        office.setWorkerId(workers);
        office.getLeaderID().add(userRepo.save(new UserModel("TestProvider", "TestProvider")));
        officeRepo.save(office);

        Set<WorkerModel> providerWorkers = trainingService.workersOfProvider(officeRepo.findAll(), userRepo.findByLogin("TestProvider"));

        assertTrue(office.equalsWorkers(providerWorkers));

        officeRepo.delete(office);
        workerRepo.delete(testWorker1);
        workerRepo.delete(testWorker2);
    }

    @Test
    void addVisitors() {
                                              //TEST WORKING GOOD!!!!!!

        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);
        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate));

        Set<WorkerModel> visitors = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        visitors.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        visitors.add(testWorker2);

        trainingService.addVisitors(new RegWorkersToTraining(testTraining.getId(), visitors));
        Optional<TrainingModel> trainingAfterAddVisitors = trainingRepo.findById(testTraining.getId());

        assertTrue(trainingAfterAddVisitors.get().equalsVisitors(visitors));

        trainingService.delete_training(testTraining.getId());
        workerRepo.delete(testWorker1);
        workerRepo.delete(testWorker2);
    }

}