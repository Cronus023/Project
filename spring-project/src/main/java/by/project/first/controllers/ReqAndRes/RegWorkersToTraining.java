package by.project.first.controllers.ReqAndRes;

import by.project.first.models.WorkerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegWorkersToTraining {

    private Long id;
    private Set<WorkerModel> newWorkers;

    public RegWorkersToTraining(Long id, Set<WorkerModel> newWorkers) {
        this.id = id;
        this.newWorkers = newWorkers;
    }

}
