package by.project.first.controllers.ReqAndRes;

import by.project.first.models.OfficeModel;
import by.project.first.models.WorkerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FindNotPassedWorkersResponse {

    private OfficeModel office;
    private Set<WorkerModel> notPassedWorkers;

    public FindNotPassedWorkersResponse(OfficeModel office, Set<WorkerModel> notPassedWorkers) {
        this.office = office;
        this.notPassedWorkers = notPassedWorkers;
    }

}
