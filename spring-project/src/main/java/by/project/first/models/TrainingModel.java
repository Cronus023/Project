package by.project.first.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
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
    private Set<WorkerModel> workerID;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training_provider",
            joinColumns = @JoinColumn(name = "trainer_id")
    )
    private UserModel trainerID;


    private String type;
    private Number numberOfSeats;

    public TrainingModel() {
    }

    public TrainingModel(Date date, Date dateOfEnd, Set<WorkerModel> workerID, UserModel trainerID, String type, Number numberOfSeats) {
        this.date = date;
        this.dateOfEnd = dateOfEnd;
        this.workerID = workerID;
        this.trainerID = trainerID;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(Date dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public Set<WorkerModel> getWorkerID() {
        return workerID;
    }

    public void setWorkerID(Set<WorkerModel> workerID) {
        this.workerID = workerID;
    }

    public UserModel getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(UserModel trainerID) {
        this.trainerID = trainerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Number numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
