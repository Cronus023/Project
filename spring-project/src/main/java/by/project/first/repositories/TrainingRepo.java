package by.project.first.repositories;

import by.project.first.models.TrainingModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface TrainingRepo extends CrudRepository<TrainingModel, Long> {
    Set<TrainingModel> findAllByTrainingPassedID(WorkerModel workerModel);
}