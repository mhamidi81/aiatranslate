package ma.aiaexpert.nat.translate.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.aiaexpert.nat.translate.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
