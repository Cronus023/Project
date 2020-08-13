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


    public Message saveTraining (AddTrainingRequest request){
        TrainingModel training = request.getTraining();
        String login = request.getUserLogin();
        UserModel user = userRepo.findByLogin(login);
        if(user == null){
            return new Message("Wrong user login");
        }
        training.setTrainerID(user);
        trainingRepo.save(training);
        return new Message("ok");
    }
    public Iterable<WorkerModel> findByIdAndUserLogin (GetWorkersTrainingRequest request){
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        UserModel user = userRepo.findByLogin(request.getLogin());

        if(user.getRoles().contains(RoleModel.PROVIDER)){
            Iterable<OfficeModel> offices = officeRepo.findAll();
            Set<WorkerModel> workers = workersOfProvider(offices, user);
            workers.removeAll(training.get().getWorkerID());
            return workers;
        }

        if(user.getRoles().contains(RoleModel.TRAINING_OPERATOR)){
            Iterable<WorkerModel> allWorkers = workerRepo.findAll();
            Iterator<WorkerModel> it = allWorkers.iterator();
            while(it.hasNext()){
                WorkerModel worker = it.next();
                if(training.get().getWorkerID().contains(worker)){
                    it.remove();
                }
            }
            return allWorkers;
        }
        return null;
    }

    public Message registerWorkers (RegWorkersToTraining request){
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        Date date = new Date();

        if(training.isEmpty()){
            return new Message("error");
        }
        if(date.compareTo(training.get().getDateOfEnd()) > 0){
            return new Message("Registration for this training is not possible");
        }
        if(training.get().getNumberOfSeats() <= 0){
            return new Message("The number of seats for registration - 0!");
        }
        request.getNewWorkers().forEach(worker-> training.get().getWorkerID().add(worker));
        Integer seats = training.get().getNumberOfSeats()- request.getNewWorkers().size();
        training.get().setNumberOfSeats(seats);
        if(training.get().getNumberOfSeats() < 0){
            return new Message("Select fewer workers for registration!");
        }
        trainingRepo.save(training.get());
        return new Message("ok");
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
            Set<WorkerModel> workers = workersOfProvider(offices, user);
            Set<WorkerModel> trainingWorkers = new HashSet<>();
            workers.forEach(worker->{
                if(training.get().getWorkerID().contains(worker)){
                    trainingWorkers.add(worker);
                }
            });
            return ResponseEntity.ok(new FindTrainingWorkersResponse(trainingWorkers, training.get()));
        }
        return ResponseEntity.ok(new FindTrainingWorkersResponse(training.get().getWorkerID(), training.get()));

    }

    public Set<WorkerModel> workersOfProvider (Iterable<OfficeModel> offices, UserModel user){
        Set<WorkerModel> workers = new HashSet<>();

        Iterator<OfficeModel> it = offices.iterator();
        while(it.hasNext()){
            OfficeModel office = it.next();
            if(!office.getLeaderID().contains(user)){
                it.remove();
            }
            else{
                workers.addAll(office.getWorkerId());
            }
        }
        return workers;
    }

    public ResponseEntity addPassedWorkers (RegWorkersToTraining request){
        Set<WorkerModel> passedWorkers = request.getNewWorkers();
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        if(training.isEmpty()){
            return ResponseEntity.status(400).body(new Message("error"));
        }
        training.get().getTrainingPassedID().addAll(passedWorkers);
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }
    public ResponseEntity delete_workers(Optional<TrainingModel> training, Set<WorkerModel> newWorkers){
        newWorkers.forEach(worker->{
            Long id = worker.getId();
            Optional<WorkerModel> deletedWorker = workerRepo.findById(id);
            training.get().getWorkerID().remove(deletedWorker.get());
        });
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }

    public ResponseEntity addVisitors (RegWorkersToTraining request){
        Set<WorkerModel> visitors = request.getNewWorkers();
        Optional<TrainingModel> training = trainingRepo.findById(request.getId());
        if(training.isEmpty()){
            return ResponseEntity.status(400).body(new Message("error"));
        }
        training.get().getTrainingVisitorsID().addAll(visitors);
        trainingRepo.save(training.get());
        return ResponseEntity.ok(new Message("ok!"));
    }

}
