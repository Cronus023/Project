package by.project.first.controllers.ReqAndRes;

import by.project.first.models.OfficeModel;

public class BecomeRequest {
    private OfficeModel office;
    private String token;

    public BecomeRequest(OfficeModel office, String token) {
        this.office = office;
        this.token = token;
    }

    public OfficeModel getOffice() {
        return office;
    }

    public void setOffice(OfficeModel office) {
        this.office = office;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
