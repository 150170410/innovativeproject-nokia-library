package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.services.ReviewService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(Mappings.GET_ALL)
    public ResponseEntity getAllReviews(){
        return MessageInfo.success(reviewService.getAllReviews(), Arrays.asList("list of reviews"));
    }

    @GetMapping(Mappings.GET_ONE)
    public ResponseEntity getReviewById(@PathVariable Long id){
        return MessageInfo.success(reviewService.getReviewById(id), Arrays.asList("Review of ID = " + id.toString()));
    }

    @PostMapping(Mappings.CREATE)
    public ResponseEntity createReview(@RequestBody @Valid ReviewDTO reviewDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(reviewService.createReview(reviewDTO), Arrays.asList("Review created successfully"));
    }

    @PostMapping(Mappings.UPDATE)
    public ResponseEntity updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDTO reviewDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(reviewService.updateReview(id, reviewDTO), Arrays.asList("Review updated successfully"));
    }

    @DeleteMapping(Mappings.REMOVE)
    public ResponseEntity deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return MessageInfo.success(null, Arrays.asList("Review with ID = " + id.toString() + " removed successfully"));
    }
}