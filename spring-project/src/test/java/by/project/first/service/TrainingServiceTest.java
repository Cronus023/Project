package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.Message;
import by.project.first.models.TrainingModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @MockBean
    private TrainingRepo trainingRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private WorkerRepo workerRepo;

    @Test
    void saveTrainingTest() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        TrainingModel testTraining = new TrainingModel(testDate);

        UserModel testTrainer = new UserModel("testTrainer", "testTrainer");

        Mockito.doReturn(testTrainer)
                .when(userRepo)
                .findByLogin("testTrainer");

        trainingService.saveTraining(new AddTrainingRequest(testTraining, "testTrainer"));

        Mockito.verify(trainingRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testTraining));

        assertEquals(testTrainer.getLogin(), testTraining.getTrainerID().getLogin());
    }

    @Test
    void registerWorkersTest() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() + 3);

        TrainingModel testTraining = new TrainingModel(testDate, 10);
        Set<WorkerModel> newWorkers = registerWorkersTestConfig(testTraining);

        trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));

        assertEquals(8, testTraining.getNumberOfSeats());
        assertEquals(newWorkers, testTraining.getWorkerID());

        Mockito.verify(trainingRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testTraining));
    }

    Set<WorkerModel> registerWorkersTestConfig(TrainingModel testTraining) {
        Mockito.doReturn(Optional.of(testTraining))
                .when(trainingRepo)
                .findById(testTraining.getId());

        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        newWorkers.add(testWorker1);
        WorkerModel testWorker2 = new WorkerModel("testWorker2");
        newWorkers.add(testWorker2);

        return newWorkers;
    }

    @Test
    void registerWorkersTestBadDate() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        TrainingModel testTraining = new TrainingModel(testDate, 10);
        Set<WorkerModel> newWorkers = registerWorkersTestConfig(testTraining);

        ResponseEntity<Message> response = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertNotNull(response.getBody());
        assertEquals("Registration for this training is not possible", response.getBody().getTitle());

        Mockito.verify(trainingRepo, Mockito.times(0))
                .save(ArgumentMatchers.eq(testTraining));
    }

    @Test
    void registerWorkersBadSeats() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() + 3);

        TrainingModel testTraining = new TrainingModel(testDate, 1);
        Set<WorkerModel> newWorkers = registerWorkersTestConfig(testTraining);

        ResponseEntity<Message> response = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertNotNull(response.getBody());
        assertEquals("Select fewer workers for registration!", response.getBody().getTitle());

        Mockito.verify(trainingRepo, Mockito.times(0))
                .save(ArgumentMatchers.eq(testTraining));
    }

    @Test
    void registerWorkersZeroSeats() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() + 3);

        TrainingModel testTraining = new TrainingModel(testDate, 0);
        Set<WorkerModel> newWorkers = registerWorkersTestConfig(testTraining);

        ResponseEntity<Message> response = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertNotNull(response.getBody());
        assertEquals("The number of seats for registration - 0!", response.getBody().getTitle());

        Mockito.verify(trainingRepo, Mockito.times(0))
                .save(ArgumentMatchers.eq(testTraining));
    }

    @Test
    void addPassedWorkers() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        TrainingModel testTraining = new TrainingModel(testDate);
        Set<WorkerModel> passed = registerWorkersTestConfig(testTraining);

        trainingService.addPassedWorkers(new RegWorkersToTraining(testTraining.getId(), passed));
        assertEquals(passed, testTraining.getTrainingPassedID());

        Mockito.verify(trainingRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testTraining));
    }

    @Test
    void addVisitors() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);
        TrainingModel testTraining = new TrainingModel(testDate);
        Set<WorkerModel> visitors = registerWorkersTestConfig(testTraining);

        trainingService.addVisitors(new RegWorkersToTraining(testTraining.getId(), visitors));
        assertEquals(visitors, testTraining.getTrainingVisitorsID());

        Mockito.verify(trainingRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testTraining));
    }

    @Test
    void deleteWorkersInTraining() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        Set<WorkerModel> deletedWorkers = new HashSet<>();
        Set<WorkerModel> newWorkers = new HashSet<>();

        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        testWorker1.setId(10000L);
        newWorkers.add(testWorker1);
        deletedWorkers.add(testWorker1);

        WorkerModel testWorker2 = new WorkerModel("testWorker2");
        testWorker2.setId(20000L);
        newWorkers.add(testWorker2);
        deletedWorkers.add(testWorker2);

        WorkerModel testWorker3 = new WorkerModel("testWorker3");
        testWorker3.setId(30000L);
        newWorkers.add(testWorker3);

        TrainingModel testTraining = new TrainingModel(testDate);
        testTraining.setNumberOfSeats(10);

        deleteWorkersTestConfig(testTraining, testWorker1, testWorker2, testWorker3);
        testTraining.setWorkerID(newWorkers);

        trainingService.deleteWorkersInTraining(new RegWorkersToTraining(testTraining.getId(), deletedWorkers));

        Set<WorkerModel> lastWorkers = new HashSet<>();
        lastWorkers.add(testWorker3);

        assertEquals(testTraining.getWorkerID(), lastWorkers);
        assertEquals(12, testTraining.getNumberOfSeats());

        Mockito.verify(trainingRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testTraining));
    }

    void deleteWorkersTestConfig(TrainingModel testTraining, WorkerModel testWorker1,
                                 WorkerModel testWorker2, WorkerModel testWorker3) {
        Mockito.doReturn(Optional.of(testTraining))
                .when(trainingRepo)
                .findById(testTraining.getId());

        Mockito.doReturn(Optional.of(testWorker1))
                .when(workerRepo)
                .findById(testWorker1.getId());

        Mockito.doReturn(Optional.of(testWorker2))
                .when(workerRepo)
                .findById(testWorker2.getId());

        Mockito.doReturn(Optional.of(testWorker3))
                .when(workerRepo)
                .findById(testWorker3.getId());
    }
}