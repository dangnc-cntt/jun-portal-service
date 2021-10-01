package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.repositories.AccountRepository;
import com.jun.portalservice.domain.repositories.BannerRepository;
import com.jun.portalservice.domain.repositories.ConfigRepository;
import com.jun.portalservice.domain.repositories.VoucherRepository;
import com.jun.portalservice.domain.utils.Caching;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseStorage {
  @Autowired protected Caching caching;

  @Autowired protected VoucherRepository voucherRepository;
  @Autowired protected AccountRepository accountRepository;
  @Autowired protected BannerRepository bannerRepository;
  @Autowired protected ConfigRepository configRepository;
}
