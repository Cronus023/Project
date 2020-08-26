package by.project.first.repositories;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.UserModel;

import org.springframework.data.repository.CrudRepository;

public interface ResponseToApplicationRepo extends CrudRepository<ResponseToApplicationModel, Long> {
    Iterable<ResponseToApplicationModel> findAllByUser(UserModel user);
    Iterable<ResponseToApplicationModel> findAllByApplicationID(ApplicationModel applicationModel);
}