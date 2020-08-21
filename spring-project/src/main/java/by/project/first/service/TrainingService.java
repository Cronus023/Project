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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainingService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private OfficeRepo officeRepo;


    public ResponseEntity saveTraining (AddTrainingRequest request){
        TrainingModel training = request.getTraining();
        UserModel user = userRepo.findByLogin(request.getUserLogin());
        training.setTrainerID(user);
        trainingRepo.save(training);
        return ResponseEntity.ok(new Message("ok"));
    }

    public ResponseEntity<Iterable<WorkerModel>> findByIdAndUserLogin (GetWorkersTrainingRequest request){
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        UserModel user = userRepo.findByLogin(request.getLogin());

        if(user.getRoles().contains(RoleModel.PROVIDER)){
            Iterable<OfficeModel> offices = officeRepo.findAll();
            Set<WorkerModel> workers = workersOfProvider(offices, user);
            workers.removeAll(training.get().getWorkerID());
            return ResponseEntity.ok(workers);
        }

        if(user.getRoles().contains(RoleModel.TRAINING_OPERATOR)){
            Iterable<WorkerModel> allWorkers = workerRepo.findAll();
            Set<WorkerModel> workers = new HashSet<>();
            allWorkers.forEach(worker->{
                if(!training.get().getWorkerID().contains(worker)){
                    workers.add(worker);
                }
            });
            return ResponseEntity.ok(workers);
        }
        return null;
    }

    public ResponseEntity<Message> registerWorkers (RegWorkersToTraining request){
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        Date date = new Date();
        if(date.compareTo(training.get().getDateOfEnd()) > 0){
            return ResponseEntity.ok(new Message("Registration for this training is not possible"));
        }
        if(training.get().getNumberOfSeats() <= 0){
            return ResponseEntity.ok(new Message("The number of seats for registration - 0!"));
        }
        request.getNewWorkers().forEach(worker-> training.get().getWorkerID().add(worker));
        Integer seats = training.get().getNumberOfSeats()- request.getNewWorkers().size();
        training.get().setNumberOfSeats(seats);
        if(training.get().getNumberOfSeats() < 0){
            return ResponseEntity.ok(new Message("Select fewer workers for registration!"));
        }
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok"));

    }

    public ResponseEntity edit_training(TrainingModel request){
        trainingRepo.save(request);
        return ResponseEntity.ok(new Message("ok!"));
    }


    public ResponseEntity findTrainingWorkers (GetWorkersTrainingRequest request){
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());

        Date date = new Date();

        if(date.compareTo(training.get().getDate()) <= 0){
            return ResponseEntity.status(400).body( new Message("It's too early"));
        }

        UserModel user = userRepo.findByLogin(request.getLogin());

        if(user.getRoles().contains(RoleModel.PROVIDER)){
            Iterable<OfficeModel> offices = officeRepo.findAll();
            Set<WorkerModel> workersOfProvider = workersOfProvider(offices, user);
            Set<WorkerModel> workersOfTraining = new HashSet<>();

            workersOfProvider.forEach(worker->{
                if(training.get().getWorkerID().contains(worker)){
                    workersOfTraining.add(worker);
                }
            });
            return ResponseEntity.ok(new FindTrainingWorkersResponse(workersOfTraining, training.get()));
        }
        return ResponseEntity.ok(new FindTrainingWorkersResponse(training.get().getWorkerID(), training.get()));
    }

    public Set<WorkerModel> workersOfProvider (Iterable<OfficeModel> offices, UserModel user){
        Set<WorkerModel> workers = new HashSet<>();
        for(OfficeModel office: offices){
            for(UserModel provider: office.getLeaderID()){
                if(provider.equals(user)){
                    workers.addAll(office.getWorkerId());
                    break;
                }
            }
        }
        return workers;
    }

    public ResponseEntity addPassedWorkers (RegWorkersToTraining request){
        Set<WorkerModel> passedWorkers = request.getNewWorkers();
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        training.get().getTrainingPassedID().addAll(passedWorkers);
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity<Message> delete_workers(RegWorkersToTraining request){
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        training.get().getWorkerID().removeAll(request.getNewWorkers());
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity delete_training(Long id){
        trainingRepo.deleteById(id);
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity addVisitors (RegWorkersToTraining request){
        Set<WorkerModel> visitors = request.getNewWorkers();
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        training.get().getTrainingVisitorsID().addAll(visitors);
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }

}
