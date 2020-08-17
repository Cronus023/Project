package by.project.first.models.ApplicationModels;

import by.project.first.models.UserModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class ResponseToApplicationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String responseStatus;

    private String typeOfSection;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "responses_owners",
            joinColumns = @JoinColumn(name = "response_id")
    )
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "response_application",
            joinColumns = @JoinColumn(name = "response_id")
    )
    private ApplicationModel applicationID;

    private Date dateOfResponse;

    public ResponseToApplicationModel() {
    }

    public ResponseToApplicationModel(String responseStatus, String typeOfSection, UserModel user, ApplicationModel applicationID, Date dateOfResponse) {
        this.responseStatus = responseStatus;
        this.typeOfSection = typeOfSection;
        this.user = user;
        this.applicationID = applicationID;
        this.dateOfResponse = dateOfResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getTypeOfSection() {
        return typeOfSection;
    }

    public void setTypeOfSection(String typeOfSection) {
        this.typeOfSection = typeOfSection;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ApplicationModel getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(ApplicationModel applicationID) {
        this.applicationID = applicationID;
    }

    public Date getDateOfResponse() {
        return dateOfResponse;
    }

    public void setDateOfResponse(Date dateOfResponse) {
        this.dateOfResponse = dateOfResponse;
    }
}
