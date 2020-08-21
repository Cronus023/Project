package by.project.first.models;

import javax.persistence.*;

@Entity
@Table
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
    public boolean equals (Object o){
        if(o == null || this.getClass() != o.getClass()){
            return false;
        }
        WorkerModel worker = (WorkerModel) o;
        return worker.getId().equals(this.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
