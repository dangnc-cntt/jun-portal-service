package com.jun.portalservice.domain.utils;

import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class LockManager {
  public static final String LOCK_PREFIX = "wiinvent:lock";

  public static final int TIME_LOCK_IN_SECOND = 2;

  @Autowired private RedissonClient client;

  public RLock startLockTenant(List<Integer> tenantIds) {
    RLock[] lockTenants = new RLock[tenantIds.size()];

    for (int i = 0; i < tenantIds.size(); i++) {
      lockTenants[i] = client.getLock(LOCK_PREFIX + ":user:" + tenantIds.get(i));
    }

    RLock lock = client.getMultiLock(lockTenants);
    lock.lock(30, TimeUnit.SECONDS);
    return lock;
  }

  public RLock startLockUpdateUser(int tenantId) {
    RLock lock = client.getLock(LOCK_PREFIX + ":user:" + tenantId);
    lock.lock(TIME_LOCK_IN_SECOND, TimeUnit.SECONDS);
    return lock;
  }

  public void unLock(RLock lock) {
    if (lock != null) {
      lock.unlockAsync();
    }
  }
}
