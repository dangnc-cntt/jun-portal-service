package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.BannerDTO;
import com.jun.portalservice.app.dtos.ConfigDTO;
import com.jun.portalservice.app.dtos.SortBannerDTO;
import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.entities.types.PositionBanner;
import com.jun.portalservice.domain.services.ConfigService;
import com.jun.portalservice.app.dtos.BannerCreateDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Banner;
import com.jun.portalservice.domain.services.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("v1/portal/configs")
public class BannerConfigController {
  @Autowired private BannerService bannerService;
  @Autowired private ConfigService configService;

  @GetMapping("banner_config")
  public ResponseEntity<PageResponse> getList(Pageable pageable) {
    return ResponseEntity.ok(configService.list(pageable));
  }

  @GetMapping(path = "banner_config/{key}")
  public ResponseEntity<Config> detail(@PathVariable String key) {
    return ResponseEntity.ok(configService.detail(key));
  }

  @PostMapping(path = "banner_config")
  public ResponseEntity<String> create(@RequestBody @Valid ConfigDTO configDTO) {
    configService.create(configDTO);

    return ResponseEntity.ok("OK");
  }

  @DeleteMapping(path = "banner_config/{key}")
  public ResponseEntity<String> delete(@PathVariable String key) {
    configService.delete(key);

    return ResponseEntity.ok("OK");
  }

  @GetMapping(path = "banners")
  public ResponseEntity<PageResponse> getBanners(
          @RequestParam PositionBanner position, Pageable pageable) {
    return ResponseEntity.ok(bannerService.getBanners(position, pageable));
  }

  @GetMapping(path = "banners/search")
  public Banner searchBanner(@RequestParam Integer bannerId) {
    return bannerService.searchBanner(bannerId);
  }

  @GetMapping(path = "banners/{id}")
  public ResponseEntity<Banner> getBanners(@PathVariable Integer id) {
    return ResponseEntity.ok(bannerService.getBannerDetail(id));
  }

  @PostMapping(path = "banners")
  public ResponseEntity<Banner> createBanner(
      @RequestParam PositionBanner position, @RequestBody @Valid BannerCreateDTO dto) {

    return ResponseEntity.ok(bannerService.createBanner(position, dto));
  }

  @PutMapping(path = "banners/publish")
  public ResponseEntity<Boolean> publishBanner() {
    return ResponseEntity.ok(bannerService.publishBanner());
  }

  @PutMapping(path = "banners/{id}")
  public ResponseEntity<Banner> updateBanner(
      @PathVariable Integer id, @RequestBody @Valid BannerDTO dto) throws IOException {
    return ResponseEntity.ok(bannerService.updateBanner(id, dto));
  }

  @DeleteMapping(path = "banners/{id}")
  public ResponseEntity<Boolean> deleteBanner(@PathVariable Integer id) throws IOException {
    return ResponseEntity.ok(bannerService.deleteBanner(id));
  }

  @PostMapping(path = "banners/sort")
  public ResponseEntity<String> sortBanner(
      @RequestParam PositionBanner position, @RequestBody SortBannerDTO sortBannerDTO) {
    try {
      bannerService.sortBanner(position, sortBannerDTO);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok("OK");
  }
}
