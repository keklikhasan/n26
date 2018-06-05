package com.hasankeklik.n26.test.managers;

import com.hasankeklik.n26.managers.TimeManager;
import com.hasankeklik.n26.managers.impl.TimeManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class TimeManagerImplTest {

  @Test
  public void validateTransaction() {
    TimeManager timeManager = new TimeManagerImpl(60000, 1000);
    long time = System.currentTimeMillis() * 1000;
    Assert.assertTrue(timeManager.validateTransaction(time, time - 54000));
    Assert.assertFalse(timeManager.validateTransaction(time, time - 154000));
  }


}
