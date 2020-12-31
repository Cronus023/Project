package by.project.first.controllers.ReqAndRes;

import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FindTrainingWorkersResponse {

    private Set<WorkerModel> trainingWorkers;
    private TrainingModel training;

    public FindTrainingWorkersResponse(Set<WorkerModel> trainingWorkers, TrainingModel training) {
        this.trainingWorkers = trainingWorkers;
        this.training = training;
    }

}
