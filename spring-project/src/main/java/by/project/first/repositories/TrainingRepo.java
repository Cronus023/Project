package by.project.first.repositories;

import by.project.first.models.TrainingModel;
import org.springframework.data.repository.CrudRepository;

public interface TrainingRepo extends CrudRepository<TrainingModel, Long> {
}