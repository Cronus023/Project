package by.project.first.models.ApplicationModels;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class ReasonsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long workerID;
    private String reason;

    public ReasonsModel() {
    }

}
