package by.project.first.models.ApplicationModels;

import by.project.first.models.OfficeModel;

import javax.persistence.*;
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


    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "application_office",
            joinColumns = @JoinColumn(name = "application_id")
    )
    private OfficeModel office;
    private String educationalProgram;
    private String additionalInfo;

    public ApplicationModel() {
    }

    public ApplicationModel(Set<ReasonsModel> reasons, Set<GroupsModel> groups, OfficeModel office, String educationalProgram, String additionalInfo) {
        this.reasons = reasons;
        this.groups = groups;
        this.office = office;
        this.educationalProgram = educationalProgram;
        this.additionalInfo = additionalInfo;
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

    public OfficeModel getOffice() {
        return office;
    }

    public void setOffice(OfficeModel office) {
        this.office = office;
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
}
