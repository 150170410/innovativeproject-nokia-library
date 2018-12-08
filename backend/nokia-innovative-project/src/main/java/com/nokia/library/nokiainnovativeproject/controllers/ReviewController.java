package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.services.ReviewService;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import com.nokia.library.nokiainnovativeproject.validators.BindingResultsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOK_REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(GET_ALL)
    public MessageInfo getAllReviews(){
        return MessageInfo.success(reviewService.getAllReviews(), Arrays.asList("list of reviews"));
    }

    @GetMapping(GET_ONE)
    public MessageInfo getReviewById(@PathVariable Long id){
        return MessageInfo.success(reviewService.getReviewById(id), Arrays.asList("Review of ID = " + id.toString()));
    }

    @PostMapping(CREATE)
    public MessageInfo createReview(@RequestBody @Valid ReviewDTO reviewDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, reviewDTO.getClass().getSimpleName());
        return MessageInfo.success(reviewService.createReview(reviewDTO), Arrays.asList("Review created successfully"));
    }

    @PostMapping(UPDATE)
    public MessageInfo updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDTO reviewDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, reviewDTO.getClass().getSimpleName());
        return MessageInfo.success(reviewService.updateReview(id, reviewDTO), Arrays.asList("Review updated successfully"));
    }

    @DeleteMapping(REMOVE)
    public MessageInfo deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return MessageInfo.success(null, Arrays.asList("Review with ID = " + id.toString() + " removed successfully"));
    }
}
