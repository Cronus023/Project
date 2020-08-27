package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ApplicationModel;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepo extends CrudRepository<ApplicationModel, Long> {
}