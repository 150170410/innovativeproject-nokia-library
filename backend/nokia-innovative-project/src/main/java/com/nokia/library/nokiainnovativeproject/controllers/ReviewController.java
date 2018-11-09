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
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(Mappings.GET_ALL)
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping(Mappings.GET_ONE)
    public Review getReviewById(@PathVariable Long id){
        return reviewService.getReviewById(id);
    }

    @PostMapping(Mappings.SAVE)
    public Review createReview(@RequestBody ReviewDTO reviewDTO){
        return reviewService.createReview(reviewDTO);
    }

    @PostMapping(Mappings.UPDATE)
    public Review updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO){
        return reviewService.updateReview(id, reviewDTO);
    }

    @DeleteMapping(Mappings.REMOVE)
    public void deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
    }
}
