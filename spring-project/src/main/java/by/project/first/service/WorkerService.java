package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.Message;
import by.project.first.models.OfficeModel;
import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class WorkerService {

    private final WorkerRepo workerRepo;
    private final TrainingRepo trainingRepo;
    private final OfficeRepo officeRepo;

    @Autowired
    public WorkerService(WorkerRepo workerRepo, TrainingRepo trainingRepo, OfficeRepo officeRepo) {
        this.workerRepo = workerRepo;
        this.trainingRepo = trainingRepo;
        this.officeRepo = officeRepo;
    }

    public Set<WorkerModel> getWorkersInOffice(String name) {
        return officeRepo.findByName(name).getWorkerId();
    }

    public WorkerModel getWorkerById(Long id) {
        return workerRepo.findById(id).get();
    }

    public Message addWorker(AddWorkerRequest request) {
        WorkerModel newWorker = workerRepo.save(request.getWorker());

        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.getWorkerId().add(newWorker);

        officeRepo.save(office);
        return new Message("ok!");
    }

    public Iterable<TrainingModel> viewTrainings(Long id) {
        return trainingRepo.findAllByTrainingPassedID(workerRepo.findById(id).get());
    }

    public Message deleteWorker(DeleteWorkerRequest request) {
        OfficeModel office = officeRepo.findByName(request.getOfficeName());
        office.setWorkerId(request.getNewWorkers());

        updateTrainingsAfterDeleteWorkers(request.getDeletedWorkers());
        officeRepo.save(office);

        Iterable<WorkerModel> deletedWorkers = request.getDeletedWorkers();
        workerRepo.deleteAll(deletedWorkers);
        return new Message("ok!");
    }

    public void updateTrainingsAfterDeleteWorkers(Set<WorkerModel> deletedWorkers) {
        trainingRepo.findAll().forEach(training -> deletedWorkers.forEach(deletedWorker -> {
            Optional<WorkerModel> w = workerRepo.findById(deletedWorker.getId());
            if (training.getWorkerID().contains(deletedWorker)) {
                Integer newSeats = training.getNumberOfSeats() + 1;
                training.getWorkerID().remove(deletedWorker);
                training.getTrainingPassedID().remove(deletedWorker);
                training.getTrainingVisitorsID().remove(deletedWorker);
                training.setNumberOfSeats(newSeats);
                trainingRepo.save(training);
            }
        }));
    }

    public Message editWorker(WorkerModel worker) {
        workerRepo.save(worker);
        return new Message("ok!");
    }

}
