package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
    Long countBookCategoriesByBookCategoryName(String bookCategoryName);

    @Query(value = "SELECT COUNT(*) FROM book_details_categories WHERE category_id = :id ;",
            nativeQuery = true)
    Long countBookDetailsByCategory(@Param("id") Long category_id);
}
