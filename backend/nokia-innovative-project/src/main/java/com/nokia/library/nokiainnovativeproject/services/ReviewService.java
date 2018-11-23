package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public  Review getReviewById(Long id){
        return reviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("ReviewService", "id", id));
    }

    public Review createReview(ReviewDTO reviewDTO){
        ModelMapper mapper = new ModelMapper();
        Review review = mapper.map(reviewDTO, Review.class);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, ReviewDTO reviewDTO){
        Review review = reviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("ReviewService", "id", id));
        review.setComment(reviewDTO.getComment());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("ReviewService", "id", id));
        reviewRepository.delete(review);
    }
}
