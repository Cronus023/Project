package by.project.first.service;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.models.Message;
import by.project.first.models.RoleModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void logout() {
        ResponseEntity<Message> responseOk = userService.logout("root");
        Assertions.assertEquals(200, responseOk.getStatusCode().value());
        UserModel user = userRepo.findByLogin("root");
        assertFalse(user.isActive());

        ResponseEntity<Message> responseBad = userService.logout("lol");
        Assertions.assertEquals(400, responseBad.getStatusCode().value());
    }

    @Test
    void register() {
        UserModel userBad = userRepo.findByLogin("root");
        ResponseEntity<Message> responseBad = userService.register(userBad);
        Assertions.assertEquals(400, responseBad.getStatusCode().value());

        //test user with good parameters

        /*UserModel userOk = new UserModel("test", "test");

        ResponseEntity responseOk = userService.register(userOk);
        Assertions.assertEquals(200, responseOk.getStatusCode().value());

        String login = "test";
        UserModel registeredUser = userRepo.findByLogin(login);
        assertNotNull(registeredUser);*/
    }

    @Test
    void findByLoginAndPassword() {
        UserModel responseOk = userService.findByLoginAndPassword("root", "root");
        assertNotNull(responseOk);

        UserModel responseBad = userService.findByLoginAndPassword("root", "root1");
        assertNull(responseBad);
    }

    @Test
    void checkAuth() {
        String tokenBad = "badToken";
        ResponseEntity<Message> responseBad = userService.checkAuth(tokenBad);
        Assertions.assertEquals(400, responseBad.getStatusCode().value());

        UserModel userOk = userRepo.findByLogin("root");
        String tokenOk = jwtProvider.generateToken(userOk.getLogin());
        ResponseEntity<Message> responseOk = userService.checkAuth(tokenOk);
        Assertions.assertEquals(200, responseOk.getStatusCode().value());
    }

    @Test
    void get_roles() {
        UserModel userOk = userRepo.findByLogin("root");
        ResponseEntity<Set<RoleModel>> responseOk = userService.get_roles("root");
        Assertions.assertEquals(userOk.getRoles(), responseOk.getBody());
    }
}