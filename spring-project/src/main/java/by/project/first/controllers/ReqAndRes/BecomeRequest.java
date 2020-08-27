package by.project.first.controllers.ReqAndRes;

import by.project.first.models.OfficeModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BecomeRequest {

    private OfficeModel office;
    private String login;

    public BecomeRequest(OfficeModel office, String login) {
        this.office = office;
        this.login = login;
    }

}
