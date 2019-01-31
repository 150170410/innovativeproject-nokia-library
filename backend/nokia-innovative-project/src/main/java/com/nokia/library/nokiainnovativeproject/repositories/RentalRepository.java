package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
    List<Rental> findByBookId(Long bookId);

    @Query(value =
            "SELECT * FROM RENTAL R " +
            "LEFT JOIN BOOK B ON B.ID = R.book_catalog_number " +
            "WHERE B.ADMIN_OWNER_ID = :adminId ;",
            nativeQuery = true)
    List<Rental> findAllByAdminOwnerId(@Param("adminId") Long adminId);

    @Query(value = "SELECT * FROM RENTAL WHERE RETURN_DATE >= now() - interval '3 days';",
            nativeQuery = true)
    List<Rental> findRentalsForReminder();

    @Query(value = "SELECT * FROM RENTAL WHERE RETURN_DATE > now();",
            nativeQuery = true)
    List<Rental> findOverdueRentals();
}
