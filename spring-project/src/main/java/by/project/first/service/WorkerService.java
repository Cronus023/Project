package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.models.OfficeModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private OfficeRepo officeRepo;

    public Optional<OfficeModel> findById(Long id) {
        return officeRepo.findById(id);
    }

    public WorkerModel saveWorker(AddWorkerRequest request) {
        WorkerModel worker = request.getWorker();
        Optional<OfficeModel> office = findById(request.getOfficeId());

        return workerRepo.save(worker);
    }

}
