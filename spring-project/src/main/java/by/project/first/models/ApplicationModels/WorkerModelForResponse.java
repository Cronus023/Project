package by.project.first.models.ApplicationModels;

import by.project.first.models.WorkerModel;

public class WorkerModelForResponse {
    private WorkerModel worker;
    private String reason;
    private Iterable<ResponseToApplicationModel> responses;

    public WorkerModelForResponse(WorkerModel worker, String reason) {
        this.worker = worker;
        this.reason = reason;
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void setWorker(WorkerModel worker) {
        this.worker = worker;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
