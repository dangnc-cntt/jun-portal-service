package com.jun.portalservice.domain.utils;

public class CacheKey {

  private static final String prefix = "jun:portal";
  private static final String junPrefix = "jun";

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
    return junPrefix + "::config:banner:list" + key;
  }

  public static String genListCategoryKey() {
    return junPrefix + "::category:list";
  }

  public static String genCategoryIdKey(int id) {
    return junPrefix + "::category:" + id;
  }

  public static String genListProductByCategoryKey(int categoryId) {
    return junPrefix + "::products:category:" + categoryId;
  }

  public static String genProductKey(int productId) {
    return junPrefix + "::product:" + productId;
  }

  public static String genListProductIsHotKey() {
    return junPrefix + "::products:hot";
  }

  public static String genListOptionProductIdKey(int productId) {
    return junPrefix + "::options:product:" + productId;
  }
}
