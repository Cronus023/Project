package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddWorkerRequest {

    private WorkerModel worker;
    private String officeName;

    public AddWorkerRequest(WorkerModel worker, String officeName) {
        this.worker = worker;
        this.officeName = officeName;
    }

}
