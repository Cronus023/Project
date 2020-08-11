package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;

import java.util.Set;

public class RegWorkersToTraining {
    private Long id;
    private Iterable<WorkerModel> newWorkers;

    public RegWorkersToTraining(Long id, Iterable<WorkerModel> newWorkers) {
        this.id = id;
        this.newWorkers = newWorkers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Iterable<WorkerModel> getNewWorkers() {
        return newWorkers;
    }

    public void setNewWorkers(Iterable<WorkerModel> newWorkers) {
        this.newWorkers = newWorkers;
    }
}
