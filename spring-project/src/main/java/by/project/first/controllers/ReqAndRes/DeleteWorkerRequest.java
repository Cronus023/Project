package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;

import java.util.Set;

public class DeleteWorkerRequest {
    private Set<WorkerModel> newWorkers;
    private Set<WorkerModel> deletedWorkers;
    private String officeName;

    public DeleteWorkerRequest(Set<WorkerModel> newWorkers, Set<WorkerModel> deletedWorkers, String officeName) {
        this.newWorkers = newWorkers;
        this.deletedWorkers = deletedWorkers;
        this.officeName = officeName;
    }

    public Set<WorkerModel> getNewWorkers() {
        return newWorkers;
    }

    public void setNewWorkers(Set<WorkerModel> newWorkers) {
        this.newWorkers = newWorkers;
    }

    public Set<WorkerModel> getDeletedWorkers() {
        return deletedWorkers;
    }

    public void setDeletedWorkers(Set<WorkerModel> deletedWorkers) {
        this.deletedWorkers = deletedWorkers;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
