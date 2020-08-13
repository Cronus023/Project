package by.project.first.controllers.ReqAndRes;

import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;

import java.util.Set;

public class FindTrainingWorkersResponse {
    private Set<WorkerModel> trainingWorkers;
    private TrainingModel training;

    public FindTrainingWorkersResponse(Set<WorkerModel> trainingWorkers, TrainingModel training) {
        this.trainingWorkers = trainingWorkers;
        this.training = training;
    }

    public Set<WorkerModel> getTrainingWorkers() {
        return trainingWorkers;
    }

    public void setTrainingWorkers(Set<WorkerModel> trainingWorkers) {
        this.trainingWorkers = trainingWorkers;
    }

    public TrainingModel getTraining() {
        return training;
    }

    public void setTraining(TrainingModel training) {
        this.training = training;
    }
}
