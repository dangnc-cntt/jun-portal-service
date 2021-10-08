package com.jun.portalservice.domain.utils;

public class CacheKey {

  private static String prefix = "jun:portal";

  public static String genSessionKey(String uuid) {
    return prefix + "_session:" + uuid;
  }

  public static String genActiveVoucherByIdKey(String voucherId) {
    return prefix + "::active:voucher:" + voucherId;
  }

  public static String genVoucherListKey() {
    return prefix + "::active:voucher:list";
  }

  public static String genAccountByIdKey(Integer accountId) {
    return prefix + "::account_by_id:" + accountId;
  }

  public static String genAccountByUserNameKey(String userName) {
    return prefix + "::account_by_username:" + userName;
  }

  public static String genBannerConfigKey(String key) {
    return prefix + "::config:banner:list" + key;
  }

  public static String genListCategoryKey() {
    return prefix + "::category:list";
  }
}
