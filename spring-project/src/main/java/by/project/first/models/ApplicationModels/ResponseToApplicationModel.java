package by.project.first.models.ApplicationModels;

import by.project.first.models.UserModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class ResponseToApplicationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String responseStatus;

    private String typeOfSection;

    @OneToOne(fetch = FetchType.EAGER)
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

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ResponseToApplicationModel response = (ResponseToApplicationModel) o;
        return response.getId().equals(this.getId());
    }

}
