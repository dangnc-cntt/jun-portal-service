package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Product;
import com.jun.portalservice.domain.entities.mongo.Review;
import com.jun.portalservice.domain.entities.types.ReviewState;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import com.jun.portalservice.domain.producers.Producer;
import jun.message.ReviewMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService extends BaseService {

  @Autowired private Producer producer;

  public void processSaveReview(List<ReviewMessage> messages) {

    List<Review> reviews = new ArrayList<>();
    if (messages != null && messages.size() > 0) {
      for (ReviewMessage message : messages) {
        Review review = new Review(message);
        reviews.add(review);
      }
    }
    reviewRepository.saveAll(reviews);
  }

  public Review findById(String id) {
    Review review = reviewRepository.findReviewById(id);
    if (review == null) {
      throw new ResourceNotFoundException("No review found!");
    }
    return review;
  }

  public Review approvedState(String id) {
    Review review = reviewRepository.findReviewById(id);
    if (review == null) {
      throw new ResourceNotFoundException("No review found!");
    }
    review.setState(ReviewState.APPROVED);

    ReviewMessage message = new ReviewMessage();
    message.setContent(review.getContent() == null ? "1" : review.getContent());
    message.setProductId(review.getProductId());
    message.setCreatedBy(review.getCreatedBy());
    message.setStar(review.getStar());
    review = reviewRepository.save(review);
    producer.sendApproveReviewMessage(message);
    return review;
  }

  public void processApprovedState(List<ReviewMessage> messages) {
    Map<Integer, Product> productMap = new HashMap<>();
    Map<Integer, List<Review>> reviewMap = new HashMap<>();

    if (messages != null && messages.size() > 0) {
      for (ReviewMessage message : messages) {
        Product product = productMap.get(message.getProductId());
        if (product == null) {
          product = productRepository.findProductById(message.getProductId());
          if (product == null) {
            throw new ResourceNotFoundException("No product found");
          }
        }
        List<Review> reviews = reviewMap.get(message.getProductId());
        if (reviews == null || reviews.size() == 0) {
          reviews = new ArrayList<>();
          reviews = reviewRepository.findByProductId(message.getProductId());
          if (reviews == null || reviews.size() == 0) {
            throw new ResourceNotFoundException("No review found!");
          }
        }

        int totalStar = 0;

        for (Review review : reviews) {
          totalStar = totalStar + review.getStar();
        }
        product.setStar((float) totalStar / reviews.size());
        productMap.put(product.getId(), product);
        reviewMap.put(product.getId(), reviews);
      }
    }
    List<Product> productList = new ArrayList<>(productMap.values());
    if (productList != null || productList.size() > 0) {
      productRepository.saveAll(productList);
    }
  }

  public PageResponse<Review> filter(
      Integer productId, Integer accountId, ReviewState state, Pageable pageable) {

    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));
    if (accountId != null) {
      andConditions.add(Criteria.where("createdBy").is(accountId));
    }

    if (productId != null) {
      andConditions.add(Criteria.where("productId").is(productId));
    }
    if (state != null) {
      andConditions.add(Criteria.where("state").is(state));
    }
    Query query = new Query();
    Criteria andCriteria = new Criteria();
    query.addCriteria(andCriteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Review> reviews = reviewRepository.findAll(query, pageable);

    return PageResponse.createFrom(reviews);
  }
}
