package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.OfficeModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private OfficeRepo officeRepo;

    public WorkerModel saveWorker(AddWorkerRequest request) {
        WorkerModel worker = request.getWorker();
        WorkerModel newWorker = workerRepo.save(worker);

        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.getWorkerId().add(newWorker);

        officeRepo.save(office);

        return newWorker;
    }

    public Optional<WorkerModel> findById(Long id) {
        Optional<WorkerModel> worker = workerRepo.findById(id);
        return worker;
    }
    public OfficeModel deleteWorker (DeleteWorkerRequest request) {
        Set<WorkerModel> workers =  request.getNewWorkers();

        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.setWorkerId(workers);

        Iterable<WorkerModel> deletedWorkers = request.getDeletedWorkers();
        deletedWorkers.forEach(worker -> workerRepo.delete(worker));

        OfficeModel newOffice = officeRepo.save(office);
        return newOffice;
    }
    public WorkerModel editWorker (WorkerModel worker) {
        return workerRepo.save(worker);
    }
}
