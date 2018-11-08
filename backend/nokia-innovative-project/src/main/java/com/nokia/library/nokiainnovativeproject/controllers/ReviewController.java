package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import com.nokia.library.nokiainnovativeproject.services.ReviewService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.LIBRARY)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(Mappings.REVIEW)
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping(Mappings.REVIEW_ID)
    public Review getReviewById(@PathVariable Long id){
        return reviewService.getReviewById(id);
    }

    @PostMapping(Mappings.REVIEW)
    public Review createReview(@RequestBody ReviewDTO reviewDTO){
        return reviewService.createReview(reviewDTO);
    }

    @PostMapping(Mappings.REVIEW_ID)
    public Review updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO){
        return reviewService.updateReview(id, reviewDTO);
    }

    @DeleteMapping(Mappings.REVIEW_ID)
    public void deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
    }
}
