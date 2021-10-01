package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Banner;
import com.jun.portalservice.domain.entities.types.BannerState;
import com.jun.portalservice.domain.entities.types.PositionBanner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends MongoRepository<Banner, Integer> {
  Banner findBannerById(Integer bannerId);

  List<Banner> findByPosition(PositionBanner positionBanner);

  List<Banner> findByPositionAndStateAndIdIn(
      PositionBanner positionBanner, BannerState state, List<Integer> ids);

  List<Banner> findByStateOrderBySortAsc(BannerState state);

  Page<Banner> findByPositionOrderBySortAsc(PositionBanner positionBanner, Pageable pageable);
}
