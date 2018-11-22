package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import com.nokia.library.nokiainnovativeproject.services.ReviewService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(Mappings.GET_ALL)
    public MessageInfo getAllReviews(){
        return MessageInfo.success(reviewService.getAllReviews(), Arrays.asList("list of reviews"));
    }

    @GetMapping(Mappings.GET_ONE)
    public MessageInfo getReviewById(@PathVariable Long id){
        return MessageInfo.success(reviewService.getReviewById(id), Arrays.asList("Review of ID = " + id.toString()));
    }

    @PostMapping(Mappings.CREATE)
    public MessageInfo createReview(@RequestBody @Valid ReviewDTO reviewDTO, BindingResult bindingResult){
        return getMessageInfo(bindingResult, reviewDTO, "Review created successfully");
    }

    @PostMapping(Mappings.UPDATE)
    public MessageInfo updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDTO reviewDTO, BindingResult bindingResult){
        return getMessageInfo(bindingResult, reviewDTO, "Review updated successfully");
    }

    @DeleteMapping(Mappings.REMOVE)
    public MessageInfo deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return MessageInfo.success(null, Arrays.asList("Review with ID = " + id.toString() + " removed successfully"));
    }

    private MessageInfo getMessageInfo(BindingResult bindingResult, ReviewDTO reviewDTO, String defaultMessageForSuccess) {
        if(bindingResult.hasErrors()){
            List<String> errorsList = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return MessageInfo.failure(errorsList);
        }
        return MessageInfo.success(reviewService.createReview(reviewDTO), Arrays.asList(defaultMessageForSuccess));
    }
}
