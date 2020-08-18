package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.OfficeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ApplicationRepo extends CrudRepository<ApplicationModel, Long> {
    Set<ApplicationModel> findAllByOffice(OfficeModel office);
}