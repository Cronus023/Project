package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.OfficeModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.WorkerRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerServiceTest {

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private WorkerRepo workerRepo;


    @Autowired
    private WorkerService workerService;

    @Test
    public void get_workers() {
        OfficeModel testOffice = new OfficeModel("testOffice");
        Set<WorkerModel> newWorkers = new HashSet<>();
        WorkerModel testWorker1 = workerRepo.save(new WorkerModel("testWorker1"));
        newWorkers.add(testWorker1);
        WorkerModel testWorker2 = workerRepo.save(new WorkerModel("testWorker2"));
        newWorkers.add(testWorker2);

        testOffice.getWorkerId().addAll(newWorkers);
        officeRepo.save(testOffice);

        ResponseEntity<Set<WorkerModel>> response = workerService.get_workers("testOffice");
        assertTrue(testOffice.equalsWorkers(Objects.requireNonNull(response.getBody())));

        officeRepo.deleteById(testOffice.getId());
        workerRepo.deleteById(testWorker1.getId());
        workerRepo.deleteById(testWorker2.getId());
    }

    @Test
    public void get_worker_by_id() {

        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        workerRepo.save(testWorker1);
        ResponseEntity<WorkerModel> worker = workerService.get_worker_by_id(testWorker1.getId());
        assertEquals(worker.getBody(), testWorker1);

        workerRepo.deleteById(testWorker1.getId());
    }

    @Test
    public void saveWorker() {
        WorkerModel testWorker1 = new WorkerModel("testWorker1");
        workerRepo.save(testWorker1);

        Set<WorkerModel> newWorkers = new HashSet<>();
        newWorkers.add(testWorker1);

        OfficeModel testOffice = new OfficeModel("testOffice");
        officeRepo.save(testOffice);

        workerService.saveWorker(new AddWorkerRequest(testWorker1, "testOffice"));

        OfficeModel officeAfterSaveWorker = officeRepo.findByName("testOffice");

        assertTrue(officeAfterSaveWorker.equalsWorkers(newWorkers));

        officeRepo.deleteById(testOffice.getId());
        workerRepo.deleteById(testWorker1.getId());
    }

}