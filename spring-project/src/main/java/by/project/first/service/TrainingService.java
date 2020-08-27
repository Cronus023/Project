package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.FindTrainingWorkersResponse;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.Message;
import by.project.first.models.RoleModel;
import by.project.first.models.TrainingModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import by.project.first.repositories.OfficeRepo;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainingService {

    private final UserRepo userRepo;
    private final TrainingRepo trainingRepo;
    private final WorkerRepo workerRepo;
    private final OfficeRepo officeRepo;

    @Autowired
    public TrainingService(UserRepo userRepo, TrainingRepo trainingRepo, WorkerRepo workerRepo, OfficeRepo officeRepo) {
        this.userRepo = userRepo;
        this.trainingRepo = trainingRepo;
        this.workerRepo = workerRepo;
        this.officeRepo = officeRepo;
    }

    public Message saveTraining(AddTrainingRequest request) {
        TrainingModel training = request.getTraining();
        UserModel user = userRepo.findByLogin(request.getUserLogin());
        training.setTrainerID(user);
        trainingRepo.save(training);
        return new Message("ok");
    }

    public Iterable<WorkerModel> findWorkersByIdAndUserLogin(GetWorkersTrainingRequest request) {
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        UserModel user = userRepo.findByLogin(request.getLogin());

        if (user.getRoles().contains(RoleModel.PROVIDER))
            return findWorkersByIdAndUserLoginProvider(user, training.get());

        if (user.getRoles().contains(RoleModel.TRAINING_OPERATOR))
            return findWorkersByIdAndUserLoginTrainingOperator(training.get());
        return null;
    }

    public Iterable<WorkerModel> findWorkersByIdAndUserLoginProvider(UserModel user, TrainingModel training) {
        Set<WorkerModel> workers = workersOfProvider(user);
        workers.removeAll(training.getWorkerID());
        return workers;
    }

    public Iterable<WorkerModel> findWorkersByIdAndUserLoginTrainingOperator(TrainingModel training) {
        Set<WorkerModel> workers = new HashSet<>();
        workerRepo.findAll().forEach(worker -> {
            if (!training.getWorkerID().contains(worker)) {
                workers.add(worker);
            }
        });
        return workers;
    }

    public ResponseEntity<Message> registerWorkers(RegWorkersToTraining request) {
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());

        if (new Date().compareTo(training.get().getDateOfEnd()) > 0)
            return ResponseEntity.status(400).body(new Message("Registration for this training is not possible"));

        if (training.get().getNumberOfSeats() <= 0)
            return ResponseEntity.status(400).body(new Message("The number of seats for registration - 0!"));

        return updateTrainingAfterRegisterWorkers(request, training.get());
    }

    public ResponseEntity<Message> updateTrainingAfterRegisterWorkers(
            RegWorkersToTraining request, TrainingModel training) {
        request.getNewWorkers().forEach(worker -> training.getWorkerID().add(worker));
        Integer seats = training.getNumberOfSeats() - request.getNewWorkers().size();
        training.setNumberOfSeats(seats);

        if (training.getNumberOfSeats() < 0)
            return ResponseEntity.status(400).body(new Message("Select fewer workers for registration!"));

        trainingRepo.save(training);
        return ResponseEntity.ok(new Message("ok"));
    }

    public Message editTraining(TrainingModel request) {
        trainingRepo.save(request);
        return new Message("ok!");
    }

    public ResponseEntity findTrainingWorkers(GetWorkersTrainingRequest request) {
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());

        if (new Date().compareTo(training.get().getDate()) <= 0)
            return ResponseEntity.status(400).body(new Message("It's too early"));

        UserModel user = userRepo.findByLogin(request.getLogin());
        if (user.getRoles().contains(RoleModel.PROVIDER)) {
            return findTrainingWorkersOfProvider(training.get(), user);
        }
        return ResponseEntity.ok(new FindTrainingWorkersResponse(training.get().getWorkerID(), training.get()));
    }

    public ResponseEntity<FindTrainingWorkersResponse> findTrainingWorkersOfProvider(
            TrainingModel training, UserModel user) {
        Set<WorkerModel> workersOfProvider = workersOfProvider(user);
        Set<WorkerModel> workersOfTraining = new HashSet<>();

        workersOfProvider.forEach(worker -> {
            if (training.getWorkerID().contains(worker)) {
                workersOfTraining.add(worker);
            }
        });
        return ResponseEntity.ok(new FindTrainingWorkersResponse(workersOfTraining, training));
    }

    public Set<WorkerModel> workersOfProvider(UserModel user) {
        Set<WorkerModel> workers = new HashSet<>();
        officeRepo.findAllByLeaderID(user).forEach(office -> {
            workers.addAll(office.getWorkerId());
        });
        return workers;
    }

    public Message addPassedWorkers(RegWorkersToTraining request) {
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        training.get().getTrainingPassedID().addAll(request.getNewWorkers());
        trainingRepo.save(training.get());
        return new Message("ok!");
    }

    public Message deleteWorkersInTraining(RegWorkersToTraining request) {
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        request.getNewWorkers().forEach(deletedWorker -> {
            training.get().getWorkerID().remove(workerRepo.findById(deletedWorker.getId()).get());
        });

        Integer seats = training.get().getNumberOfSeats() + request.getNewWorkers().size();
        training.get().setNumberOfSeats(seats);

        trainingRepo.save(training.get());
        return new Message("ok!");
    }

    public Message deleteTraining(Long id) {
        trainingRepo.deleteById(id);
        return new Message("ok!");
    }

    public ResponseEntity<Message> addVisitors(RegWorkersToTraining request) {
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        training.get().getTrainingVisitorsID().addAll(request.getNewWorkers());
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }

}
