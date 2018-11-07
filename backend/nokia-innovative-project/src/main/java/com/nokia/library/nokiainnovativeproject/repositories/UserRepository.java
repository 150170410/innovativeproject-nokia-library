package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
