package by.project.first.controllers.ReqAndRes;

import by.project.first.models.TrainingModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTrainingRequest {

    private TrainingModel training;
    private String userLogin;

    public AddTrainingRequest(TrainingModel training, String userLogin) {
        this.training = training;
        this.userLogin = userLogin;
    }

}
