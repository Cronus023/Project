package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;

public class AddWorkerRequest {
    private WorkerModel worker;
    private String officeName;

    public AddWorkerRequest(WorkerModel worker, String officeName) {
        this.worker = worker;
        this.officeName = officeName;
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void setWorker(WorkerModel worker) {
        this.worker = worker;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
