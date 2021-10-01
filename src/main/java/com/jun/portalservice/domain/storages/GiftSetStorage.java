package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.GiftSet;
import com.jun.portalservice.domain.repositories.GiftSetRepository;
import com.jun.portalservice.domain.utils.CacheKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class GiftSetStorage extends BaseStorage {
  @Autowired private GiftSetRepository giftSetRepository;

  public GiftSet findGiftSetById(int id) {
    return giftSetRepository.findGiftSetById(id);
  }

  public void save(GiftSet giftSet) {
    giftSetRepository.save(giftSet);

    if (giftSet.getIsDefault()) {
      caching.put(CacheKey.genGiftSetDefaultKey(), giftSet);
    }
    processCache();
  }

  public void processCache() {
    List<GiftSet> giftSetList = giftSetRepository.findAll();
    caching.put(CacheKey.genGiftSetListKey(), giftSetList);
  }

  public void delete(GiftSet giftSet) {
    giftSetRepository.delete(giftSet);

    processCache();
  }

  public List<GiftSet> findAll() {
    return giftSetRepository.findAll();
  }

  public GiftSet findGiftSetByIsDefault(boolean isDefault) {
    return giftSetRepository.findGiftSetByIsDefault(isDefault);
  }
}
