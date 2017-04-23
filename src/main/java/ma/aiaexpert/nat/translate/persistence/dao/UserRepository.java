package ma.aiaexpert.nat.translate.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.aiaexpert.nat.translate.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

}
