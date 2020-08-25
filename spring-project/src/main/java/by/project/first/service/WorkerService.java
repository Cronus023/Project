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
    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private OfficeRepo officeRepo;

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

        updateTrainingsAfterDeleteWorkers(request);
        officeRepo.save(office);

        Iterable<WorkerModel> deletedWorkers = request.getDeletedWorkers();
        workerRepo.deleteAll(deletedWorkers);
        return new Message("ok!");
    }

    public void updateTrainingsAfterDeleteWorkers(DeleteWorkerRequest request) {
        trainingRepo.findAll().forEach(training -> request.getDeletedWorkers().forEach(deletedWorker -> {
            Optional<WorkerModel> w = workerRepo.findById(deletedWorker.getId());
            if (training.getWorkerID().contains(w.get())) {
                Integer newSeats = training.getNumberOfSeats() + 1;
                training.getWorkerID().remove(w.get());
                training.getTrainingPassedID().remove(w.get());
                training.getTrainingVisitorsID().remove(w.get());
                training.setNumberOfSeats(newSeats);
            }
            trainingRepo.save(training);
        }));
    }

    public Message editWorker(WorkerModel worker) {
        workerRepo.save(worker);
        return new Message("ok!");
    }
}
