package ma.aiaexpert.nat.translate.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ma.aiaexpert.nat.translate.persistence.dao.UserRepository;
import ma.aiaexpert.nat.translate.persistence.dao.VerificationTokenRepository;
import ma.aiaexpert.nat.translate.persistence.model.User;
import ma.aiaexpert.nat.translate.persistence.model.VerificationToken;
import ma.aiaexpert.nat.translate.spring.ServiceConfig;
import ma.aiaexpert.nat.translate.spring.TestDbConfig;
import ma.aiaexpert.nat.translate.spring.TestIntegrationConfig;
import ma.aiaexpert.nat.translate.validation.EmailExistsException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestDbConfig.class, ServiceConfig.class, TestIntegrationConfig.class })
@Transactional
public class UserIntegrationTest {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Long tokenId;
    private Long userId;

    //

    @Before
    public void givenUserAndVerificationToken() throws EmailExistsException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("SecretPassword");
        user.setFirstName("First");
        user.setLastName("Last");
        entityManager.persist(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        entityManager.persist(verificationToken);

        entityManager.flush();
        entityManager.clear();

        tokenId = verificationToken.getId();
        userId = user.getId();
    }

    @After
    public void flushAfter() {
        entityManager.flush();
        entityManager.clear();
    }

    //

    @Test
    public void whenContextLoad_thenCorrect() {
        assertEquals(1, userRepository.count());
        assertEquals(1, tokenRepository.count());
    }

    // @Test(expected = Exception.class)
    @Test
    @Ignore("needs to go through the service and get transactional semantics")
    public void whenRemovingUser_thenFkViolationException() {
        userRepository.delete(userId);
    }

    @Test
    public void whenRemovingTokenThenUser_thenCorrect() {
        tokenRepository.delete(tokenId);
        userRepository.delete(userId);
    }

}
