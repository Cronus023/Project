package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.controllers.ReqAndRes.GetWorkersTrainingRequest;
import by.project.first.controllers.ReqAndRes.RegWorkersToTraining;
import by.project.first.models.*;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import by.project.first.repositories.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainingService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private WorkerRepo workerRepo;

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

       /* if(user.getRoles().contains(RoleModel.PROVIDER)){
            return new Message("PROVIDER");
        }*/

        if(user.getRoles().contains(RoleModel.TRAINING_OPERATOR)){
            boolean flag = false;
            Set<WorkerModel> newWorkers = null;
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
        if(training.isEmpty()){
            return new Message("error");
        }
        request.getNewWorkers().forEach(worker-> training.get().getWorkerID().add(worker));
        trainingRepo.save(training.get());
        return new Message("ok");
    }
}
