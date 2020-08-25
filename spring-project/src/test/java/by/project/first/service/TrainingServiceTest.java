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

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
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

   /* @Test
    void saveTraining() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        TrainingModel testTraining = new TrainingModel(testDate);

        UserModel testTrainer = userRepo.save(new UserModel("testTrainer", "testTrainer"));

        trainingService.saveTraining(new AddTrainingRequest(testTraining, "testTrainer"));

        assertNotNull(testTraining.getId());
        assertEquals(testTrainer, testTraining.getTrainerID());

        trainingRepo.deleteById(testTraining.getId());
        userRepo.deleteById(testTrainer.getId());
    }

    @Test
    void registerWorkersOkDate() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() + 3);

        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        newWorkers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        newWorkers.add(testWorker2);


        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate, 10));

        trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));

        Optional<TrainingModel> testTrainingAfterUpdate = trainingRepo.findById(testTraining.getId());
        assertNotNull(testTrainingAfterUpdate.get());

        assertEquals(testTrainingAfterUpdate.get().getNumberOfSeats(), 8);

        boolean checkWorkers = checkWorkers(newWorkers, testTrainingAfterUpdate.get().getWorkerID());
        assertFalse(checkWorkers);


        trainingRepo.deleteById(testTrainingAfterUpdate.get().getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    void registerWorkersBadDate() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);

        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        newWorkers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        newWorkers.add(testWorker2);


        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate, 10));

        ResponseEntity<Message> response = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertNotNull(response.getBody());
        assertEquals("Registration for this training is not possible", response.getBody().getTitle());


        Optional<TrainingModel> testTrainingAfterUpdate = trainingRepo.findById(testTraining.getId());
        assertNotNull(testTrainingAfterUpdate.get());

        trainingRepo.deleteById(testTrainingAfterUpdate.get().getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    void registerWorkersBadSeats() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() + 3);

        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        newWorkers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        newWorkers.add(testWorker2);

        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate, 1));

        ResponseEntity<Message> response = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertNotNull(response.getBody());
        assertEquals("Select fewer workers for registration!", response.getBody().getTitle());

        Optional<TrainingModel> testTrainingAfterUpdate = trainingRepo.findById(testTraining.getId());
        assertNotNull(testTrainingAfterUpdate.get());

        trainingRepo.deleteById(testTrainingAfterUpdate.get().getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    void registerWorkersZeroSeats() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() + 3);

        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        newWorkers.add(testWorker1);

        TrainingModel testTraining = trainingRepo.save( new TrainingModel(testDate, 0));

        ResponseEntity<Message> response = trainingService.registerWorkers(new RegWorkersToTraining(testTraining.getId(), newWorkers));
        assertNotNull(response.getBody());
        assertEquals("The number of seats for registration - 0!", response.getBody().getTitle());

        Optional<TrainingModel> testTrainingAfterUpdate = trainingRepo.findById(testTraining.getId());
        assertNotNull(testTrainingAfterUpdate.get());

        trainingRepo.deleteById(testTrainingAfterUpdate.get().getId());
        workerRepo.deleteById(testWorker1.getId());
    }



    @Test
    void addPassedWorkers() {
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

        boolean checkWorkers = checkWorkers(passed, trainingAfterAddPassed.get().getTrainingPassedID());
        assertFalse(checkWorkers);

        trainingService.delete_training(testTraining.getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    void delete_training() {
        Date testDate = new Date();
        testDate.setYear(testDate.getYear() - 1);
        TrainingModel testTraining = trainingRepo.save(new TrainingModel(testDate));
        trainingService.delete_training(testTraining.getId());
        assertTrue(trainingRepo.findById(testTraining.getId()).isEmpty());
    }

    @Test
    void findTrainingWorkers() {
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

        assertNotNull(response.getBody());

        boolean checkWorkers = checkWorkers(workers, response.getBody().getTrainingWorkers());
        assertFalse(checkWorkers);


        trainingService.delete_training(testTraining.getId());

        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    void workersOfProvider() {
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

        boolean checkWorkers = checkWorkers(workers, providerWorkers);
        assertFalse(checkWorkers);

        UserModel provider = userRepo.findByLogin("TestProvider");

        officeRepo.deleteById(office.getId());
        userRepo.deleteById(provider.getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    void addVisitors() {
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

        boolean checkWorkers = checkWorkers(visitors, trainingAfterAddVisitors.get().getTrainingVisitorsID());
        assertFalse(checkWorkers);

        trainingService.delete_training(testTraining.getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }


    public boolean checkWorkers(Set<WorkerModel> workersBeforeMethod,Set<WorkerModel> workersAfterMethod ){
        boolean checkWorkers = false;
        for(WorkerModel worker : workersBeforeMethod){
            boolean checkWorker = false;
            for(WorkerModel visitedWorker: workersAfterMethod){
                if(worker.equals(visitedWorker)){
                    checkWorker = true;
                    break;
                }
            }
            if(!checkWorker){
                checkWorkers = true;
                break;
            }
        }
        return checkWorkers;
    }*/
}