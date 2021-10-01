package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Banner;
import com.jun.portalservice.domain.entities.types.BannerState;
import com.jun.portalservice.domain.entities.types.PositionBanner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BannerStorage extends BaseStorage {

  public Page<Banner> findByPositionOrderBySortAsc(
      PositionBanner positionBanner, Pageable pageable) {
    return bannerRepository.findByPositionOrderBySortAsc(positionBanner, pageable);
  }

  public Page<Banner> findAll(Pageable pageable) {
    return bannerRepository.findAll(pageable);
  }

  public Banner findById(int bannerId) {
    return bannerRepository.findBannerById(bannerId);
  }

  public List<Banner> findByPositionAndStateAndIdIn(
      PositionBanner positionBanner, BannerState state, List<Integer> ids) {
    return bannerRepository.findByPositionAndStateAndIdIn(positionBanner, state, ids);
  }

  public List<Banner> findByStateOrderBySortAsc(BannerState state) {
    return bannerRepository.findByStateOrderBySortAsc(state);
  }

  public List<Banner> findByPosition(PositionBanner positionBanner) {
    return bannerRepository.findByPosition(positionBanner);
  }

  public Banner save(Banner banner) {
    return bannerRepository.save(banner);
  }

  public void saveAll(List<Banner> bannerList) {
    bannerRepository.saveAll(bannerList);
  }

  public void delete(Banner banner) {
    bannerRepository.delete(banner);
  }
}
