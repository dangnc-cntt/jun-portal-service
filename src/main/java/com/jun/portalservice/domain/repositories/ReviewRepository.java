package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Review;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoResourceRepository<Review, String> {
  Review findReviewById(String id);

  List<Review> findByProductId(Integer productId);
}
