package by.server.eureka.controllers;

import by.server.eureka.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login/getMessage")
    public Message getTrainings() {
        String url = "http://localhost:9090/auth/main";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        return new Message(response);
    }
}
