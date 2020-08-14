package by.project.first.controllers.ReqAndRes;

import by.project.first.models.OfficeModel;
import by.project.first.models.WorkerModel;

import java.util.Set;

public class FindNotPassedWorkersResponse {
    private OfficeModel office;
    private Set<WorkerModel> notPassedWorkers;

    public FindNotPassedWorkersResponse(OfficeModel office, Set<WorkerModel> notPassedWorkers) {
        this.office = office;
        this.notPassedWorkers = notPassedWorkers;
    }

    public OfficeModel getOffice() {
        return office;
    }

    public void setOffice(OfficeModel office) {
        this.office = office;
    }

    public Set<WorkerModel> getNotPassedWorkers() {
        return notPassedWorkers;
    }

    public void setNotPassedWorkers(Set<WorkerModel> notPassedWorkers) {
        this.notPassedWorkers = notPassedWorkers;
    }
}
