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

    public OfficeModel(String location, String name, Date dateOfLastPermission, String contact_details, Set<UserModel> leaderID, String photo, Set<WorkerModel> workerId, Set<ApplicationModel> officeApplications) {
        this.location = location;
        this.name = name;
        this.dateOfLastPermission = dateOfLastPermission;
        this.contact_details = contact_details;
        this.leaderID = leaderID;
        this.photo = photo;
        this.workerId = workerId;
        this.officeApplications = officeApplications;
    }
    @Override
    public boolean equals (Object o){
        if(o == null || this.getClass() != o.getClass()){
            return false;
        }
        OfficeModel office = (OfficeModel) o;
        return office.getId().equals(this.getId());
    }

    public boolean equalsWorkers (Set<WorkerModel> workers){
        if(this.getWorkerId().size() != workers.size()){
            return false;
        }
        boolean check = false;
        for(WorkerModel worker: this.getWorkerId()){
            boolean flag = false;
            for(WorkerModel workerC: workers){
                if(worker.equals(workerC)){
                    flag = true;
                    break;
                }
            }
            if(!flag) break;
            else check = true;
        }
        return check;
    }

    public ApplicationModel getLastApplication() {
        return lastApplication;
    }

    public void setLastApplication(ApplicationModel lastApplication) {
        this.lastApplication = lastApplication;
    }

    public Set<ApplicationModel> getOfficeApplications() {
        return officeApplications;
    }

    public void setOfficeApplications(Set<ApplicationModel> officeApplications) {
        this.officeApplications = officeApplications;
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
