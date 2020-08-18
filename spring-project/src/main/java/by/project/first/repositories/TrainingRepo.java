package by.project.first.repositories;

import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;
import org.springframework.data.repository.CrudRepository;

public interface TrainingRepo extends CrudRepository<TrainingModel, Long> {
    Iterable<TrainingModel> findAllByTrainingPassedID(WorkerModel workerModel);
}