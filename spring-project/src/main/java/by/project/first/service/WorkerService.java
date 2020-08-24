package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.*;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private OfficeRepo officeRepo;

    public ResponseEntity<Set<WorkerModel>> get_workers(String name){
        OfficeModel office = officeRepo.findByName(name);
        return ResponseEntity.ok(office.getWorkerId());
    }

    public ResponseEntity<WorkerModel> get_worker_by_id(Long id){
        Optional<WorkerModel> worker = workerRepo.findById(id);
        return ResponseEntity.ok(worker.get());
    }

    public ResponseEntity<Message> saveWorker(AddWorkerRequest request) {
        WorkerModel worker = request.getWorker();
        WorkerModel newWorker = workerRepo.save(worker);

        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.getWorkerId().add(newWorker);

        officeRepo.save(office);
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Iterable<TrainingModel>> view_trainings(Long id) {
        Optional<WorkerModel> worker = workerRepo.findById(id);
        Iterable<TrainingModel> workerTrainings = trainingRepo.findAllByTrainingPassedID(worker.get());
        return ResponseEntity.ok(workerTrainings);
    }


    public ResponseEntity<Message> deleteWorker (DeleteWorkerRequest request) {
        Set<WorkerModel> workers = request.getNewWorkers();

        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.setWorkerId(workers);

        Iterable<TrainingModel> trainings = trainingRepo.findAll();

        trainings.forEach(training->{
            request.getDeletedWorkers().forEach(deletedWorker->{
                Optional<WorkerModel> w = workerRepo.findById(deletedWorker.getId());
                if(training.getWorkerID().contains(w.get())){
                    Integer newSeats = training.getNumberOfSeats() + +1 ;
                    training.getWorkerID().remove(w.get());
                    training.getTrainingPassedID().remove(w.get());
                    training.getTrainingVisitorsID().remove(w.get());
                    training.setNumberOfSeats(newSeats);
                }
                trainingRepo.save(training);
            });
        });
        officeRepo.save(office);

        Iterable<WorkerModel> deletedWorkers = request.getDeletedWorkers();
        workerRepo.deleteAll(deletedWorkers);

        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Message> editWorker (WorkerModel worker) {
        workerRepo.save(worker);
        return ResponseEntity.ok(new Message("ok!"));
    }
}
