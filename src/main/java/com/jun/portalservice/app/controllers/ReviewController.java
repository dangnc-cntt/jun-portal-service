package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Review;
import com.jun.portalservice.domain.entities.types.ReviewState;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/reviews")
public class ReviewController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<Review>> filter(
      @RequestParam(required = false) Integer productId,
      @RequestParam(required = false) Integer accountId,
      @RequestParam(required = false) ReviewState state,
      Pageable pageable,
      @RequestHeader(name = "x-jun-portal-token") String token) {
    validateToken(token);
    return ResponseEntity.ok(reviewService.filter(productId, accountId, state, pageable));
  }

  @GetMapping("{reviewId}")
  public ResponseEntity<Review> findById(
      @PathVariable("reviewId") String reviewId,
      @RequestHeader(name = "x-jun-portal-token") String token) {
    validateToken(token);
    return ResponseEntity.ok(reviewService.findById(reviewId));
  }

  @PutMapping("{reviewId}")
  public ResponseEntity<Review> approveReview(
      @PathVariable("reviewId") String reviewId,
      @RequestHeader(name = "x-jun-portal-token") String token) {
    validateToken(token);
    return ResponseEntity.ok(reviewService.approvedState(reviewId));
  }
}
