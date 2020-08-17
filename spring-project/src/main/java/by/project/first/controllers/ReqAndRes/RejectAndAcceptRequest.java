package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;

public class RejectAndAcceptRequest {
    private String login;
    private ApplicationModel application;
    private String status;

    public RejectAndAcceptRequest(String login, ApplicationModel application, String status) {
        this.login = login;
        this.application = application;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ApplicationModel getApplication() {
        return application;
    }

    public void setApplication(ApplicationModel application) {
        this.application = application;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
