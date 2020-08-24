package by.project.first.models.ApplicationModels;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
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

    public ApplicationModel(Set<ReasonsModel> reasons, Set<GroupsModel> groups, String educationalProgram, String additionalInfo, String officeName, String officeLocation) {
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
        return application.getId().equals(this.getId());
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(Date dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ReasonsModel> getReasons() {
        return reasons;
    }

    public void setReasons(Set<ReasonsModel> reasons) {
        this.reasons = reasons;
    }

    public Set<GroupsModel> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupsModel> groups) {
        this.groups = groups;
    }

    public String getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(String educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
