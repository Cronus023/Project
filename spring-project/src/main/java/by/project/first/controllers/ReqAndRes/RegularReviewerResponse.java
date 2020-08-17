package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.WorkerModelForResponse;
import by.project.first.models.WorkerModel;

import java.util.Set;

public class RegularReviewerResponse {
    private Set<WorkerModelForResponse> workers;
    private ApplicationModel application;

    public RegularReviewerResponse(Set<WorkerModelForResponse> workers, ApplicationModel application) {
        this.workers = workers;
        this.application = application;
    }

    public Set<WorkerModelForResponse> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<WorkerModelForResponse> workers) {
        this.workers = workers;
    }

    public ApplicationModel getApplication() {
        return application;
    }

    public void setApplication(ApplicationModel application) {
        this.application = application;
    }
}
