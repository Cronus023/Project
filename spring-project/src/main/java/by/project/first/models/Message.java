package by.project.first.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String title;

    public Message(String title) {
        this.title = title;
    }

}
