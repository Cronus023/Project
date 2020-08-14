package by.project.first.models.ApplicationModels;

import javax.persistence.*;

@Entity
@Table
public class ReasonsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long workerID;
    private String reason;

    public ReasonsModel() {
    }

    public ReasonsModel(Long id, Long workerID, String reason) {
        this.id = id;
        this.workerID = workerID;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkerID() {
        return workerID;
    }

    public void setWorkerID(Long workerID) {
        this.workerID = workerID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
