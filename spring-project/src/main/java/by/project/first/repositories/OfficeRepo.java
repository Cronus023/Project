package by.project.first.repositories;

import by.project.first.models.OfficeModel;
import org.springframework.data.repository.CrudRepository;

public interface OfficeRepo extends CrudRepository<OfficeModel, Long> {
    OfficeModel findByName(String name);
}