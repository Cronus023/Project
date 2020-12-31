package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ReasonsModel;
import org.springframework.data.repository.CrudRepository;

public interface ReasonRepo extends CrudRepository<ReasonsModel, Long> {
}