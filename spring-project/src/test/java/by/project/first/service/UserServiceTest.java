package by.project.first.service;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.models.Message;
import by.project.first.models.RoleModel;
import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

   /* @Test
    void login() {
        UserModel testUser = new UserModel("testUser", "testUser");
        userService.register(testUser);

        userService.login(new UserModel("testUser", "testUser"));
        Optional<UserModel> userAfterLogin = userRepo.findById(testUser.getId());

        assertFalse(userAfterLogin.isEmpty());
        userRepo.deleteById(userAfterLogin.get().getId());

    }

    @Test
    void logoutOK() {
        UserModel testUser = new UserModel("testUser", "testUser");
        userRepo.save(testUser);

        userService.logout("testUser");

        UserModel testUserAfterLogout = userRepo.findByLogin("testUser");
        assertFalse(testUserAfterLogout.isActive());
        userRepo.delete(testUserAfterLogout);
    }

    @Test
    void logoutBAD() {
        ResponseEntity<Message> responseBad = userService.logout("badUserTest");
        Assertions.assertEquals(400, responseBad.getStatusCode().value());
    }

    @Test
    void registerOK() {
        UserModel testUser = new UserModel("testUser", "testUser");
        userService.register(testUser);
        assertNotNull(userRepo.findById(testUser.getId()));

        userRepo.deleteById(testUser.getId());
    }

    @Test
    void registerBAD() {
        UserModel userBad = userRepo.findByLogin("root");
        ResponseEntity<Message> responseBad = userService.register(userBad);
        Assertions.assertEquals(400, responseBad.getStatusCode().value());
    }

    @Test
    void findByLoginAndPassword() {
        UserModel testUser = new UserModel("testUser", "testUser");
        userService.register(testUser);
        UserModel responseTest = userService.findByLoginAndPassword("testUser", "testUser");
        assertNotNull(responseTest);
        userRepo.deleteById(testUser.getId());
    }

    @Test
    void checkAuthOK() {
        UserModel userOk = userRepo.findByLogin("root");
        String tokenOk = jwtProvider.generateToken(userOk.getLogin());
        ResponseEntity<Message> responseOk = userService.checkAuth(tokenOk);
        Assertions.assertEquals(200, responseOk.getStatusCode().value());
    }

    @Test
    void checkAuthBAD() {
        String tokenBad = "badToken";
        ResponseEntity<Message> responseBad = userService.checkAuth(tokenBad);
        Assertions.assertEquals(400, responseBad.getStatusCode().value());
    }

    @Test
    void get_roles() {
        UserModel userOk = userRepo.findByLogin("root");
        ResponseEntity<Set<RoleModel>> responseOk = userService.get_roles("root");
        Assertions.assertEquals(userOk.getRoles(), responseOk.getBody());
    }*/
}