package by.project.first.controllers.ReqAndRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetWorkersTrainingRequest {

    private Long id;
    private String login;

    public GetWorkersTrainingRequest(Long id) {
        this.id = id;
    }

    public GetWorkersTrainingRequest(Long id, String login) {
        this.id = id;
        this.login = login;
    }

}
