package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;

public class AddWorkerRequest {
    private WorkerModel worker;
    private Long officeId;

    public AddWorkerRequest(WorkerModel worker, Long officeId) {
        this.worker = worker;
        this.officeId = officeId;
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void setWorker(WorkerModel worker) {
        this.worker = worker;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }
}
