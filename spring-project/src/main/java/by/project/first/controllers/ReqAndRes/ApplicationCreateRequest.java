package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;
import by.project.first.models.OfficeModel;

public class ApplicationCreateRequest {
    private ApplicationModel application;
    private OfficeModel office;

    public ApplicationCreateRequest(ApplicationModel application, OfficeModel office) {
        this.application = application;
        this.office = office;
    }

    public ApplicationModel getApplication() {
        return application;
    }

    public void setApplication(ApplicationModel application) {
        this.application = application;
    }

    public OfficeModel getOffice() {
        return office;
    }

    public void setOffice(OfficeModel office) {
        this.office = office;
    }
}
