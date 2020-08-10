package by.project.first.repositories;

import by.project.first.models.WorkerModel;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepo extends CrudRepository<WorkerModel, Long> {
}