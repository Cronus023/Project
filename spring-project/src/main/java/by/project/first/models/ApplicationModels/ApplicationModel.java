package by.project.first.models.ApplicationModels;

import lombok.Data;

import javax.persistence.*;
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
                            Set<GroupsModel> groups, String educationalProgram, String additionalInfo, String officeName,
                            String officeLocation) {
        this.reasons = reasons;
        this.groups = groups;
        this.educationalProgram = educationalProgram;
        this.additionalInfo = additionalInfo;
        this.officeName = officeName;
        this.officeLocation = officeLocation;
    }
}
