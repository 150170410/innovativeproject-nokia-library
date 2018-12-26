package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT email FROM \"user\" u INNER JOIN user_roles ur ON u.id = ur.user_id " +
            "INNER JOIN role r ON ur.roles_id = r.id WHERE r.role = 'ROLE_ADMIN'",
            nativeQuery = true)
    List<String> getAdminsEmail();

    User findUserByEmail(String email);
}
