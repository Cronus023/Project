package by.project.first.controllers.ReqAndRes;

import by.project.first.models.RoleModel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

public class LoginResponse {
    private String token;

    @Enumerated(EnumType.STRING)
    private Set<RoleModel> roles;


    public LoginResponse(String token, Set<RoleModel> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

}
