package by.project.first.controllers.ReqAndRes;

import by.project.first.models.RoleModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Getter
@Setter
public class LoginResponse {

    private String token;

    @Enumerated(EnumType.STRING)
    private Set<RoleModel> roles;

    public LoginResponse(String token, Set<RoleModel> roles) {
        this.token = token;
        this.roles = roles;
    }

}
