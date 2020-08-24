package by.project.first.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<WorkerModel> getTrainingVisitorsID() {
        return trainingVisitorsID;
    }

    public void setTrainingVisitorsID(Set<WorkerModel> trainingVisitorsID) {
        this.trainingVisitorsID = trainingVisitorsID;
    }

    public Set<WorkerModel> getTrainingPassedID() {
        return trainingPassedID;
    }

    public void setTrainingPassedID(Set<WorkerModel> trainingPassedID) {
        this.trainingPassedID = trainingPassedID;
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

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
