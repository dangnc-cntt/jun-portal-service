package com.jun.portalservice.domain.utils;

public class CacheKey {

  private static String prefix = "wiinvent:loyalty:portal";
  private static String LOYALTY_APP_PREFIX = "wiinvent:loyalty";

  public static String genSessionKey(String uuid) {
    return prefix + "_session:" + uuid;
  }

  public static String genBeanConfigKey(String code) {
    return LOYALTY_APP_PREFIX + "::bean:config:" + code;
  }

  public static String genActiveVoucherByIdKey(String voucherId) {
    return LOYALTY_APP_PREFIX + "::active:voucher:" + voucherId;
  }

  public static String genVoucherListKey() {
    return LOYALTY_APP_PREFIX + "::active:voucher:list";
  }

  public static String genAccountByIdKey(String accountId) {
    return LOYALTY_APP_PREFIX + "::account_by_id:" + accountId;
  }

  public static String genAccountByUserNameKey(String userName) {
    return LOYALTY_APP_PREFIX + "::account_by_username:" + userName;
  }

  public static String genAccountByReferralIdKey(long referralId) {
    return LOYALTY_APP_PREFIX + "::account_by_referralId:" + referralId;
  }

  public static String genAccountRankListKey() {
    return LOYALTY_APP_PREFIX + "::user_ranks";
  }

  public static String genGiftSetDefaultKey() {
    return LOYALTY_APP_PREFIX + "::gift_set_default";
  }

  public static String genGiftSetListKey() {
    return LOYALTY_APP_PREFIX + "::gift_set_list";
  }

  public static String genLuckySpinGiftListTypeLostKey() {
    return LOYALTY_APP_PREFIX + "::luckyspin_gift_list_type_lost";
  }

  public static String genLuckySpinGiftByIdKey(String id) {
    return LOYALTY_APP_PREFIX + "::luckyspin:gift:" + id;
  }

  public static String genLuckySpinGiftAllKey() {
    return LOYALTY_APP_PREFIX + "::luckyspin_gift_list";
  }

  public static String genLuckySpinSlotKey(int giftSetId) {
    return LOYALTY_APP_PREFIX + "::luckyspin_slot:gift_set_id:" + giftSetId;
  }

  public static String genBannerConfigKey(String key) {
    return LOYALTY_APP_PREFIX + "::config:banner:list" + key;
  }
}
