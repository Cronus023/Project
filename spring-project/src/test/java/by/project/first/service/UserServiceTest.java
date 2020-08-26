package by.project.first.service;

import by.project.first.config.jwt.JwtProvider;
import by.project.first.models.Message;
import by.project.first.models.UserModel;
import by.project.first.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void loginTest() {
        UserModel testUser = new UserModel("testUser", "testUser");

        Mockito.doReturn(testUser)
                .when(userRepo)
                .findByLogin("testUser");

        Mockito.doReturn(true)
                .when(passwordEncoder)
                .matches("testUser", "testUser");

        userService.login(testUser);

        loginTestVerify(testUser);
        assertTrue(testUser.isActive());
    }

    void loginTestVerify(UserModel testUser) {
        Mockito.verify(passwordEncoder, Mockito.times(1))
                .matches("testUser", "testUser");

        Mockito.verify(userRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testUser));

        Mockito.verify(jwtProvider, Mockito.times(1))
                .generateToken(ArgumentMatchers.eq(testUser.getLogin()));
    }

    @Test
    void loginFailTest() {
        UserModel testUser = new UserModel("testUser", "testUser");
        userService.login(testUser);

        loginFailTestVerify();
    }

    void loginFailTestVerify() {
        Mockito.verify(passwordEncoder, Mockito.times(0))
                .matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

        Mockito.verify(userRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(UserModel.class));

        Mockito.verify(jwtProvider, Mockito.times(0))
                .generateToken(ArgumentMatchers.anyString());
    }

    @Test
    void logoutTest() {
        UserModel testUser = new UserModel("testUser", "testUser");

        Mockito.doReturn(testUser)
                .when(userRepo)
                .findByLogin("testUser");

        userService.logout("testUser");

        Mockito.verify(userRepo, Mockito.times(1))
                .save(ArgumentMatchers.eq(testUser));

        assertFalse(testUser.isActive());
    }

    @Test
    void logoutFailTest() {
        ResponseEntity<Message> response = userService.logout("testUser");

        assertNotNull(response.getBody());
        assertEquals("error", response.getBody().getTitle());

        Mockito.verify(userRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(UserModel.class));
    }

    @Test
    void registerTest() {
        UserModel testUser = new UserModel();

        ResponseEntity<Message> response = userService.register(testUser);
        registerTestCheckResponseAndVerify(response, testUser);
    }

    void registerTestCheckResponseAndVerify(ResponseEntity<Message> response, UserModel testUser) {
        Assertions.assertEquals(200, response.getStatusCode().value());

        Mockito.verify(userRepo, Mockito.times(1))
                .save(testUser);

        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode(ArgumentMatchers.eq(testUser.getPassword()));
    }

    @Test
    void registerFailTest() {
        UserModel testUser = new UserModel();
        testUser.setLogin("Test");

        Mockito.doReturn(new UserModel())
                .when(userRepo)
                .findByLogin("Test");

        ResponseEntity<Message> response = userService.register(testUser);
        registerFailTestCheckResponseAndVerify(response);
    }

    void registerFailTestCheckResponseAndVerify(ResponseEntity<Message> response) {
        Assertions.assertNotNull(response.getBody());
        assertEquals("Such user already exist!", response.getBody().getTitle());

        Mockito.verify(userRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(UserModel.class));

        Mockito.verify(passwordEncoder, Mockito.times(0))
                .encode(ArgumentMatchers.anyString());
    }

    @Test
    void checkAuthFailTest() {
        String token = "badToken";

        ResponseEntity<Message> response = userService.checkAuth(token);

        Assertions.assertEquals(400, response.getStatusCode().value());

        Mockito.verify(jwtProvider, Mockito.times(1))
                .validateToken(ArgumentMatchers.eq(token));
    }

}