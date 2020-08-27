package by.project.first.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class TrainingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;
    private Date dateOfEnd;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training_participants",
            joinColumns = @JoinColumn(name = "training_id")
    )
    private Set<WorkerModel> workerID = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training_visitors",
            joinColumns = @JoinColumn(name = "training_id")
    )
    private Set<WorkerModel> trainingVisitorsID = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training_passing",
            joinColumns = @JoinColumn(name = "training_id")
    )
    private Set<WorkerModel> trainingPassedID = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training_provider",
            joinColumns = @JoinColumn(name = "trainer_id")
    )
    private UserModel trainerID;

    private String type;
    private Integer numberOfSeats;

    public TrainingModel() {
    }

    public TrainingModel(Date date) {
        this.date = date;
    }

    public TrainingModel(Date dateOfEnd, Integer numberOfSeats) {
        this.dateOfEnd = dateOfEnd;
        this.numberOfSeats = numberOfSeats;
    }

    public TrainingModel(Date date, Date dateOfEnd, Set<WorkerModel> workerID, Set<WorkerModel> trainingVisitorsID, Set<WorkerModel> trainingPassedID, UserModel trainerID, String type, Integer numberOfSeats) {
        this.date = date;
        this.dateOfEnd = dateOfEnd;
        this.workerID = workerID;
        this.trainingVisitorsID = trainingVisitorsID;
        this.trainingPassedID = trainingPassedID;
        this.trainerID = trainerID;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TrainingModel training = (TrainingModel) o;
        return training.getId().equals(this.getId());
    }

}
