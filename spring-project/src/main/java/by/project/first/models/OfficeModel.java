package by.project.first.models;

import javax.persistence.*;

@Entity
@Table
public class OfficeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String location;
    private String name;

    private String contact_details;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id")
    private UserModel leaderID;
    private String photo;

    public OfficeModel(String location, String name, String contact_details, UserModel leaderID, String photo) {
        this.location = location;
        this.name = name;
        this.contact_details = contact_details;
        this.leaderID = leaderID;
        this.photo = photo;
    }

    public OfficeModel() {
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

    public UserModel getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(UserModel leaderID) {
        this.leaderID = leaderID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
