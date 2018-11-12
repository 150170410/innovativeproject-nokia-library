package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import com.nokia.library.nokiainnovativeproject.services.ReviewService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(Mappings.GET_ALL)
    public MessageInfo getAllReviews(){
        return new MessageInfo(true, reviewService.getAllReviews(), "list of reviews");
    }

    @GetMapping(Mappings.GET_ONE)
    public MessageInfo getReviewById(@PathVariable Long id){
        return new MessageInfo(true, reviewService.getReviewById(id), "Review of ID = " + id.toString());
    }

    @PostMapping(Mappings.SAVE)
    public MessageInfo createReview(@RequestBody ReviewDTO reviewDTO){
        return new MessageInfo(true, reviewService.createReview(reviewDTO), "Review created successfully");
    }

    @PostMapping(Mappings.UPDATE)
    public MessageInfo updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO){
        return new MessageInfo(true, reviewService.updateReview(id, reviewDTO), "Review updated successfully");
    }

    @DeleteMapping(Mappings.REMOVE)
    public MessageInfo deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return new MessageInfo(true, null, "Review with ID = " + id.toString() + " removed successfully");
    }
}
