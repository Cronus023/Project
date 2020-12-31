package by.project.first.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WorkerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String education;
    private String email;
    private String name;

    public WorkerModel() {
    }

    public WorkerModel(String name) {
        this.name = name;
    }

    public WorkerModel(String education, String email, String name) {
        this.education = education;
        this.email = email;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        WorkerModel worker = (WorkerModel) o;
        return worker.getId().equals(this.getId());
    }

}
