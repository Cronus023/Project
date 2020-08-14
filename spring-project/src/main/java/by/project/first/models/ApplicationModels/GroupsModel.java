package by.project.first.models.ApplicationModels;

import javax.persistence.*;

@Entity
@Table
public class GroupsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String activities;
    private Integer numberOfClasses;

    public GroupsModel() {
    }

    public GroupsModel(Long id, String name, String activities, Integer numberOfClasses) {
        this.id = id;
        this.name = name;
        this.activities = activities;
        this.numberOfClasses = numberOfClasses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }
}
