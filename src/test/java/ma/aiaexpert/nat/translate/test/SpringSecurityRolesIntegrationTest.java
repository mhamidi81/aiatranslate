package ma.aiaexpert.nat.translate.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ma.aiaexpert.nat.translate.persistence.dao.PrivilegeRepository;
import ma.aiaexpert.nat.translate.persistence.dao.RoleRepository;
import ma.aiaexpert.nat.translate.persistence.dao.UserRepository;
import ma.aiaexpert.nat.translate.persistence.model.Privilege;
import ma.aiaexpert.nat.translate.persistence.model.Role;
import ma.aiaexpert.nat.translate.persistence.model.User;
import ma.aiaexpert.nat.translate.spring.TestDbConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDbConfig.class)
@Transactional
public class SpringSecurityRolesIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    private Role role;
    private Privilege privilege;

    // tests

    @Test
    public void testDeleteUser() {
        role = new Role("TEST_ROLE");
        roleRepository.save(role);

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Arrays.asList(role));
        user.setEnabled(true);
        userRepository.save(user);

        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));
        user.setRoles(null);
        userRepository.delete(user);

        assertNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));
    }

    @Test
    public void testDeleteRole() {
        privilege = new Privilege("TEST_PRIVILEGE");
        privilegeRepository.save(privilege);

        role = new Role("TEST_ROLE");
        role.setPrivileges(Arrays.asList(privilege));
        roleRepository.save(role);

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Arrays.asList(role));
        user.setEnabled(true);
        userRepository.save(user);

        assertNotNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));

        user.setRoles(new ArrayList<Role>());
        role.setPrivileges(new ArrayList<Privilege>());
        roleRepository.delete(role);

        assertNull(roleRepository.findByName(role.getName()));
        assertNotNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(userRepository.findByEmail(user.getEmail()));
    }

    @Test
    public void testDeletePrivilege() {
        privilege = new Privilege("TEST_PRIVILEGE");
        privilegeRepository.save(privilege);

        role = new Role("TEST_ROLE");
        role.setPrivileges(Arrays.asList(privilege));
        roleRepository.save(role);

        assertNotNull(roleRepository.findByName(role.getName()));
        assertNotNull(privilegeRepository.findByName(privilege.getName()));

        role.setPrivileges(new ArrayList<Privilege>());
        privilegeRepository.delete(privilege);

        assertNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(roleRepository.findByName(role.getName()));
    }
}
