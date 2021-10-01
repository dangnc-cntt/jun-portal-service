package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.BannerDTO;
import com.jun.portalservice.app.dtos.ConfigDTO;
import com.jun.portalservice.app.dtos.SortBannerDTO;
import com.jun.portalservice.domain.data.BannerConfig;
import com.jun.portalservice.domain.data.Constants;
import com.jun.portalservice.domain.entities.types.BannerState;
import com.jun.portalservice.domain.entities.types.PositionBanner;
import com.jun.portalservice.app.dtos.BannerCreateDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Banner;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import com.jun.portalservice.domain.utils.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@SuppressWarnings("Duplicates")
public class BannerService extends BaseService {

  @Autowired protected ConfigService configService;

  public PageResponse getBanners(PositionBanner positionBanner, Pageable pageable) {
    Page<Banner> banners = bannerStorage.findByPositionOrderBySortAsc(positionBanner, pageable);
    return PageResponse.createFrom(banners);
  }

  public Banner searchBanner(int bannerId) {
    Banner banner = bannerStorage.findById(bannerId);
    if (banner == null) {
      throw new ResourceNotFoundException("Banner with id " + bannerId + " not found!");
    }
    return banner;
  }

  public Banner getBannerDetail(int id) {
    Banner banner = bannerStorage.findById(id);
    if (banner == null) {
      throw new ResourceNotFoundException("Banner with id " + id + " not found!");
    }
    return banner;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Banner createBanner(PositionBanner position, BannerCreateDTO dto) {

    List<Banner> bannerList = bannerStorage.findByPosition(position);

    Banner banner = new Banner();
    banner.setId((int) generateSequence(Banner.SEQUENCE_NAME));
    banner.setBannerUrl(dto.getBannerUrl());
    banner.setPosition(position);
    banner.setLinkTo(dto.getLinkTo());
    banner.setLinkParam1(dto.getLinkParam1());
    banner.setLinkParam2(dto.getLinkParam2());
    banner.setSort(bannerList.size() + 1);
    banner.setState(BannerState.NEW);
    banner = bannerStorage.save(banner);
    return banner;
  }

  public boolean publishBanner() {

    List<Banner> activeBanner = bannerStorage.findByStateOrderBySortAsc(BannerState.ACTIVE);

    List<BannerConfig> configs =
        activeBanner.stream()
            .filter(banner -> banner.getPosition().equals(PositionBanner.HOME_TOP))
            .map(banner -> new BannerConfig().assignFrom(banner))
            .collect(Collectors.toList());
    // Home Top
    ConfigDTO configDTO = new ConfigDTO();

    configDTO.setValue(JsonParser.toJson(configs));
    configDTO.setKey(Constants.BANNER_HOME_TOP);
    configService.create(configDTO);

    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Banner updateBanner(int id, BannerDTO dto) throws IOException {
    Banner banner = bannerStorage.findById(id);

    if (banner == null) {
      throw new ResourceNotFoundException("Banner with id " + id + " not found!");
    }

    banner.setBannerUrl(dto.getBannerUrl());
    banner.setLinkTo(dto.getLinkTo());
    banner.setLinkParam1(dto.getLinkParam1());
    banner.setLinkParam2(dto.getLinkParam2());
    //    banner.setPosition(dto.getPositionBanner());
    banner.setState(dto.getState());
    banner = bannerStorage.save(banner);

    return banner;
  }

  public boolean deleteBanner(int id) throws IOException {
    Banner banner = bannerStorage.findById(id);

    if (banner == null) {
      throw new ResourceNotFoundException("Banner with id " + id + " not found!");
    }
    bannerStorage.delete(banner);

    return true;
  }

  public void sortBanner(PositionBanner position, SortBannerDTO sortBannerDTO) throws IOException {
    List<Banner> bannerList =
        bannerStorage.findByPositionAndStateAndIdIn(
            position, BannerState.ACTIVE, sortBannerDTO.getIds());

    for (int i = 0; i < sortBannerDTO.getIds().size(); i++) {
      int id = sortBannerDTO.getIds().get(i);
      for (int j = 0; j < bannerList.size(); j++) {
        Banner banner = bannerList.get(j);
        if (id == banner.getId()) {
          banner.setSort(i);
          continue;
        }
      }
    }

    bannerStorage.saveAll(bannerList);
  }
}
