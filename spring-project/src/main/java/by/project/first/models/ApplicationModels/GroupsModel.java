package by.project.first.models.ApplicationModels;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class GroupsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String activities;

    private Integer numberOfClasses;

    public GroupsModel() {
    }

}
