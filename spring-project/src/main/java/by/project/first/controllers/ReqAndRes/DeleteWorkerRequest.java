package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DeleteWorkerRequest {

    private Set<WorkerModel> newWorkers;
    private Set<WorkerModel> deletedWorkers;
    private String officeName;

    public DeleteWorkerRequest(Set<WorkerModel> newWorkers, Set<WorkerModel> deletedWorkers, String officeName) {
        this.newWorkers = newWorkers;
        this.deletedWorkers = deletedWorkers;
        this.officeName = officeName;
    }

}
