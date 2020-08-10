package by.project.first.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class OfficeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String location;
    private String name;

    private String contact_details;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "office_providers",
            joinColumns = @JoinColumn(name = "office_id")
    )
    private Set<UserModel> leaderID;
    private String photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "office_workers",
            joinColumns = @JoinColumn(name = "office_id")
    )
    private Set<WorkerModel> workerId;


    public OfficeModel() {
    }



    public OfficeModel(Long id, String location, String name, String contact_details, Set<UserModel> leaderID, String photo, Set<WorkerModel> workerId) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.contact_details = contact_details;
        this.leaderID = leaderID;
        this.photo = photo;
        this.workerId = workerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_details() {
        return contact_details;
    }

    public void setContact_details(String contact_details) {
        this.contact_details = contact_details;
    }

    public Set<UserModel> getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(Set<UserModel> leaderID) {
        this.leaderID = leaderID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<WorkerModel> getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Set<WorkerModel> workerId) {
        this.workerId = workerId;
    }
}
