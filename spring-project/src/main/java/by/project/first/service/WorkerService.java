package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.*;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private TrainingRepo trainingRepo;

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
    public Iterable<TrainingModel> deleteWorker (DeleteWorkerRequest request) {
        Set<WorkerModel> workers =  request.getNewWorkers();

        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.setWorkerId(workers);

        Iterable<TrainingModel> trainings = trainingRepo.findAll();
        trainings.forEach(training->{
            request.getDeletedWorkers().forEach(deletedWorker->{
                Optional<WorkerModel> w = workerRepo.findById(deletedWorker.getId());
                if(training.getWorkerID().contains(w.get())){
                    Integer newSeats = training.getNumberOfSeats() + +1 ;
                    training.getWorkerID().remove(w.get());
                    training.setNumberOfSeats(newSeats);
                }
                trainingRepo.save(training);
            });
        });
        Iterable<WorkerModel> deletedWorkers = request.getDeletedWorkers();
        deletedWorkers.forEach(worker -> workerRepo.delete(worker));

        officeRepo.save(office);
        return trainings;
    }

    public WorkerModel editWorker (WorkerModel worker) {
        return workerRepo.save(worker);
    }
}
