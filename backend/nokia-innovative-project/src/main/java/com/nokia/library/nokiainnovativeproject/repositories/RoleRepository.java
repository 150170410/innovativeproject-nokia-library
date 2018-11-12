package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query("SELECT r FROM Role r WHERE r.role = ?1")
    List<Role> findRoleByName(String name);
}
