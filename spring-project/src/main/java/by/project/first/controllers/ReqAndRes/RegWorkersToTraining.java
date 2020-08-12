package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;

import java.util.Set;

public class RegWorkersToTraining {
    private Long id;
    private Set<WorkerModel> newWorkers;

    public RegWorkersToTraining(Long id, Set<WorkerModel> newWorkers) {
        this.id = id;
        this.newWorkers = newWorkers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<WorkerModel> getNewWorkers() {
        return newWorkers;
    }

    public void setNewWorkers(Set<WorkerModel> newWorkers) {
        this.newWorkers = newWorkers;
    }
}
