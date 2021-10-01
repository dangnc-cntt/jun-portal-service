package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.GiftSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftSetRepository extends MongoRepository<GiftSet, Integer> {
  GiftSet findGiftSetById(Integer id);

  GiftSet findGiftSetByIsDefault(Boolean isDefault);
}
