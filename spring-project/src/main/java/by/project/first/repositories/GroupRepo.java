package by.project.first.repositories;

import by.project.first.models.ApplicationModels.GroupsModel;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepo extends CrudRepository<GroupsModel, Long> {
}