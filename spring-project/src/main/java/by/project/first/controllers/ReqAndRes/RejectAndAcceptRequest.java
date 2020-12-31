package by.project.first.controllers.ReqAndRes;

import by.project.first.models.ApplicationModels.ApplicationModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectAndAcceptRequest {

    private String login;
    private ApplicationModel application;
    private String status;
    private String typeOfSection;

    public RejectAndAcceptRequest(String login, ApplicationModel application, String status, String typeOfSection) {
        this.login = login;
        this.application = application;
        this.status = status;
        this.typeOfSection = typeOfSection;
    }

}
