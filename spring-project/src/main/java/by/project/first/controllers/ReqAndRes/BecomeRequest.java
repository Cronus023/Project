package by.project.first.controllers.ReqAndRes;

import by.project.first.models.OfficeModel;

public class BecomeRequest {
    private OfficeModel office;
    private String login;

    public BecomeRequest(OfficeModel office, String login) {
        this.office = office;
        this.login = login;
    }

    public OfficeModel getOffice() {
        return office;
    }

    public void setOffice(OfficeModel office) {
        this.office = office;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
