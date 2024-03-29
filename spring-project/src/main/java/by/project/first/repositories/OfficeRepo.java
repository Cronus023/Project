package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OfficeRepo extends CrudRepository<OfficeModel, Long> {

    OfficeModel findByName(String name);

    Set<OfficeModel> findAllByLeaderID(UserModel user);

    OfficeModel findByLastApplication(ApplicationModel application);

}