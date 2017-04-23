package ma.aiaexpert.nat.translate.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.aiaexpert.nat.translate.persistence.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
