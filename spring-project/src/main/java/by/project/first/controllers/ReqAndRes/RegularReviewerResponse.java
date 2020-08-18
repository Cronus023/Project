package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.ApplicationModels.WorkerModelForResponse;

import java.util.Set;

public class RegularReviewerResponse {
    private Set<WorkerModelForResponse> workers;
    private ApplicationModel application;
    private Iterable<ResponseToApplicationModel> responses;

    public RegularReviewerResponse(Set<WorkerModelForResponse> workers, ApplicationModel application, Iterable<ResponseToApplicationModel> responses) {
        this.workers = workers;
        this.application = application;
        this.responses = responses;
    }

    public RegularReviewerResponse(ApplicationModel application, Iterable<ResponseToApplicationModel> responses) {
        this.application = application;
        this.responses = responses;
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

    public Iterable<ResponseToApplicationModel> getResponses() {
        return responses;
    }

    public void setResponses(Iterable<ResponseToApplicationModel> responses) {
        this.responses = responses;
    }
}
