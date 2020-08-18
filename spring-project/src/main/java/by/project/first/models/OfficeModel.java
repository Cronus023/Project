package by.project.first.models;

import by.project.first.models.ApplicationModels.ApplicationModel;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "office_workers",
            joinColumns = @JoinColumn(name = "office_id")
    )
    private Set<WorkerModel> workerId = new HashSet<>();

    private Long lastApplicationId;
    private String statusOfLastApplication;

    public OfficeModel() {
    }


    public OfficeModel(String location, String name, Date dateOfLastPermission, String contact_details, Set<UserModel> leaderID, String photo, Set<WorkerModel> workerId, ApplicationModel lastApplication) {
        this.location = location;
        this.name = name;
        this.dateOfLastPermission = dateOfLastPermission;
        this.contact_details = contact_details;
        this.leaderID = leaderID;
        this.photo = photo;
        this.workerId = workerId;
    }

    public String getStatusOfLastApplication() {
        return statusOfLastApplication;
    }

    public void setStatusOfLastApplication(String statusOfLastApplication) {
        this.statusOfLastApplication = statusOfLastApplication;
    }

    public Long getLastApplicationId() {
        return lastApplicationId;
    }

    public void setLastApplicationId(Long lastApplicationId) {
        this.lastApplicationId = lastApplicationId;
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

    public Date getDateOfLastPermission() {
        return dateOfLastPermission;
    }

    public void setDateOfLastPermission(Date dateOfLastPermission) {
        this.dateOfLastPermission = dateOfLastPermission;
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
