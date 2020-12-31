package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.OfficeModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationCreateRequest {

    private ApplicationModel application;
    private OfficeModel office;

    public ApplicationCreateRequest(ApplicationModel application, OfficeModel office) {
        this.application = application;
        this.office = office;
    }

}
