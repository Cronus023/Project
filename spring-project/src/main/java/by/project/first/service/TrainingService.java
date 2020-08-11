package by.project.first.service;

import by.project.first.controllers.ReqAndRes.AddTrainingRequest;
import by.project.first.models.Message;
import by.project.first.models.TrainingModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.TrainingRepo;
import by.project.first.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainingRepo trainingRepo;

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
}
