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
            "SELECT * FROM rental r WHERE r.is_current = true AND r.book_catalog_number IN " +
                    "(SELECT boi.book_id FROM book_owner_id boi where boi.owner_id = ?1);",
            nativeQuery = true)
    List<Rental> findAllByAdminOwnerId(Long adminId);

    @Query(value =
            "SELECT * FROM rental r WHERE r.is_current = false AND r.book_catalog_number IN " +
                    "(SELECT boi.book_id FROM book_owner_id boi where boi.owner_id = ?1);",
            nativeQuery = true)
    List<Rental> getAllRentalHistory(Long adminId);

    @Query(value = "SELECT * FROM RENTAL WHERE RETURN_DATE >= now() - interval '3 days';",
            nativeQuery = true)
    List<Rental> findRentalsForReminder();

    @Query(value = "SELECT * FROM RENTAL WHERE RETURN_DATE > now();",
            nativeQuery = true)
    List<Rental> findOverdueRentals();

    @Query(value = "SELECT * FROM RENTAL R " +
            "LEFT JOIN BOOK B ON R.book_catalog_number = B.ID " +
            "LEFT JOIN BOOK_STATUS BS ON B.book_status_id = BS.ID " +
            "WHERE R.RENTAL_DATE >= now() - interval '3 days' AND BS.STATUS_NAME = 'AWAITING'" ,
    nativeQuery = true)
    List<Rental> findUnacceptedRentals();
}
