package by.project.first.controllers.ReqAndRes;

import by.project.first.models.TrainingModel;

public class AddTrainingRequest {
    private TrainingModel training;
    private String userLogin;

    public AddTrainingRequest(TrainingModel training, String userLogin) {
        this.training = training;
        this.userLogin = userLogin;
    }

    public TrainingModel getTraining() {
        return training;
    }

    public void setTraining(TrainingModel training) {
        this.training = training;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
