package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.OfficeModel;
import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.WorkerRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerServiceTest {

    @Autowired
    private WorkerService workerService;

    @MockBean
    private OfficeRepo officeRepo;

    @MockBean
    private WorkerRepo workerRepo;

    @MockBean
    private TrainingRepo trainingRepo;

    @Test
    public void deleteWorkerTest() {
        OfficeModel testOffice = new OfficeModel("testOffice");

        Set<WorkerModel> newWorkers = new HashSet<>();
        Set<WorkerModel> deletedWorkers = deleteWorkerTestSetWorkers();

        Mockito.doReturn(testOffice)
                .when(officeRepo)
                .findByName("testOffice");

        workerService.deleteWorker(new DeleteWorkerRequest(newWorkers, deletedWorkers, "testOffice"));

        assertEquals(testOffice.getWorkerId(), newWorkers);
        deleteWorkerTestVerify(deletedWorkers, testOffice);
    }

    Set<WorkerModel> deleteWorkerTestSetWorkers() {
        Set<WorkerModel> deletedWorkers = new HashSet<>();
        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        deletedWorkers.add(testWorker1);
        WorkerModel testWorker2 = new WorkerModel("testWorker1");
        deletedWorkers.add(testWorker2);
        return deletedWorkers;
    }

    void deleteWorkerTestVerify(Set<WorkerModel> deletedWorkers, OfficeModel testOffice) {
        Mockito.verify(workerRepo, Mockito.times(1))
                .deleteAll(ArgumentMatchers.eq(deletedWorkers));

        Mockito.verify(workerRepo, Mockito.times(1))
                .deleteAll(ArgumentMatchers.eq(deletedWorkers));

        Mockito.verify(officeRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testOffice));

        Mockito.verify(trainingRepo, Mockito.times(1))
                .findAll();

        Mockito.verify(trainingRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(TrainingModel.class));
    }

    @Test
    public void addWorkerTest() {
        OfficeModel testOffice = new OfficeModel("testOffice");
        WorkerModel testWorker1 = new WorkerModel("testWorker1");

        Mockito.doReturn(testOffice)
                .when(officeRepo)
                .findByName("testOffice");

        Mockito.doReturn(testWorker1)
                .when(workerRepo)
                .save(testWorker1);

        workerService.addWorker(new AddWorkerRequest(testWorker1, "testOffice"));
        addWorkerTestAfterResponse(testOffice, testWorker1);
    }

    void addWorkerTestAfterResponse(OfficeModel testOffice, WorkerModel testWorker1) {
        assertTrue(testOffice.getWorkerId().contains(testWorker1));

        Mockito.verify(workerRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testWorker1));

        Mockito.verify(officeRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testOffice));
    }

}