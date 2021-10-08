package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.repositories.*;
import com.jun.portalservice.domain.utils.Caching;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseStorage {
  @Autowired protected Caching caching;

  @Autowired protected VoucherRepository voucherRepository;
  @Autowired protected AccountRepository accountRepository;
  @Autowired protected BannerRepository bannerRepository;
  @Autowired protected ConfigRepository configRepository;
  @Autowired protected CategoryRepository categoryRepository;
}
