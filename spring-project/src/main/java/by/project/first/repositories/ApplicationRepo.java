package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ApplicationModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface ApplicationRepo extends CrudRepository<ApplicationModel, Long> {
    ApplicationModel findByOfficeName(String officeName);
    Set<ApplicationModel> findAllByOfficeName(String officeName);
}