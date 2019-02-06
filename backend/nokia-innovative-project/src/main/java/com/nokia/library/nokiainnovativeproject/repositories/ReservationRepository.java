package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Reservation;
import com.nokia.library.nokiainnovativeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    @Query(value = "SELECT * FROM reservation r WHERE r.book_catalog_number = ?1 ORDER BY r.available_date",
    nativeQuery = true)
    List<Reservation> findByBookId(Long bookId);
    @Query(value = "SELECT * FROM \"user\" U " +
            "LEFT JOIN RESERVATION R ON R.USER_ID = U.ID " +
            "WHERE R.BOOK_CATALOG_NUMBER = :id ;", nativeQuery = true)
    List<User> findUserByBook(@Param("id") Long bookId);
}
