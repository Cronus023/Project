package by.project.first.models;

import by.project.first.models.ApplicationModels.ApplicationModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class OfficeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String location;

    private String name;

    private Date dateOfLastPermission;

    private String contact_details;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "office_providers",
            joinColumns = @JoinColumn(name = "office_id")
    )
    private Set<UserModel> leaderID = new HashSet<>();

    private String photo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "office_workers",
            joinColumns = @JoinColumn(name = "office_id")
    )
    private Set<WorkerModel> workerId = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "office_applications",
            joinColumns = @JoinColumn(name = "office_id")
    )
    private Set<ApplicationModel> officeApplications = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private ApplicationModel lastApplication;

    public OfficeModel() {
    }

    public OfficeModel(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        OfficeModel office = (OfficeModel) o;
        return office.getId().equals(this.getId());
    }

}
