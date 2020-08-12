package by.project.first.repositories;

import by.project.first.models.OfficeModel;
import by.project.first.models.UserModel;
import by.project.first.models.WorkerModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.Set;

public interface OfficeRepo extends CrudRepository<OfficeModel, Long> {
    OfficeModel findByName(String name);
}