package by.project.first.models;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
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

}
