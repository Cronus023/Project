package by.project.first.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String address;
    private String email;
    private String education;
    private String phone;
    private String name;
    private String surname;

    private boolean active;

    @ElementCollection(targetClass = RoleModel.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleModel> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "provider_offices",
            joinColumns = @JoinColumn(name = "provider_id")
    )
    private Set<OfficeModel> officeID = new HashSet<>();

    public UserModel() {
    }

    public UserModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserModel(String login, String password, String address, String email, String education, String phone, String name, String surname, boolean active, Set<RoleModel> roles, Set<OfficeModel> officeID) {
        this.login = login;
        this.password = password;
        this.address = address;
        this.email = email;
        this.education = education;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.roles = roles;
        this.officeID = officeID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UserModel user = (UserModel) o;
        return user.getId().equals(this.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

    public Set<OfficeModel> getOfficeID() {
        return officeID;
    }

    public void setOfficeID(Set<OfficeModel> officeID) {
        this.officeID = officeID;
    }
}
