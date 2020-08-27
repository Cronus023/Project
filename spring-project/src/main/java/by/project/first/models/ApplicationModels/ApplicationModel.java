package by.project.first.models.ApplicationModels;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class ApplicationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workers_reasons",
            joinColumns = @JoinColumn(name = "application_id")
    )
    private Set<ReasonsModel> reasons = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "application_groups",
            joinColumns = @JoinColumn(name = "application_id")
    )
    private Set<GroupsModel> groups = new HashSet<>();

    private String educationalProgram;
    private String additionalInfo;
    private Date dateOfApplication = new Date();
    private String status = "WAIT_FOR_AN_ANSWER";
    private String officeName;
    private String officeLocation;

    public ApplicationModel() {
    }

    public ApplicationModel(Set<ReasonsModel> reasons,
                            Set<GroupsModel> groups, String educationalProgram, String additionalInfo,
                            String officeName, String officeLocation) {
        this.reasons = reasons;
        this.groups = groups;
        this.educationalProgram = educationalProgram;
        this.additionalInfo = additionalInfo;
        this.officeName = officeName;
        this.officeLocation = officeLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ApplicationModel application = (ApplicationModel) o;

        //check for the test
        if (this.getId() == null && application.getId() == null) {
            return true;
        }

        return this.getId().equals(application.getId());
    }

}
