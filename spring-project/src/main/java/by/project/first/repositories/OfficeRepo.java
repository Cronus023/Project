package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public interface OfficeRepo extends CrudRepository<OfficeModel, Long> {
    OfficeModel findByName(String name);

    Iterable<OfficeModel> findAllByLeaderID(UserModel user);

    OfficeModel findByLastApplication(Optional<ApplicationModel> application);
}