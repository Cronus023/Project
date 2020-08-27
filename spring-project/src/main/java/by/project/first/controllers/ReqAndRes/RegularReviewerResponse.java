package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.ApplicationModels.ResponseToApplicationModel;
import by.project.first.models.ApplicationModels.WorkerModelForResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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

}
