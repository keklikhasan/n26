package com.hasankeklik.n26.test.models;

import com.hasankeklik.n26.managers.TimeManager;
import com.hasankeklik.n26.models.Transaction;
import com.hasankeklik.n26.models.TransactionStatistic;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class TransactionStatisticTest {

  @Test
  public void testAddTransaction() {
    long time = System.currentTimeMillis() * 1000;
    Transaction transaction = new Transaction(12, time);
    TransactionStatistic statistic = new TransactionStatistic(transaction);
    Assert.assertEquals(1, statistic.getCount());
    Assert.assertEquals(12, statistic.getAvg(), 0.001);
    Assert.assertEquals(12, statistic.getMax(), 0.001);
    Assert.assertEquals(12, statistic.getMin(), 0.001);
    Assert.assertEquals(12, statistic.getSum(), 0.001);
    transaction = new Transaction(4, time);
    statistic.addTransaction(transaction);
    Assert.assertEquals(2, statistic.getCount());
    Assert.assertEquals(8, statistic.getAvg(), 0.001);
    Assert.assertEquals(12, statistic.getMax(), 0.001);
    Assert.assertEquals(4, statistic.getMin(), 0.001);
    Assert.assertEquals(16, statistic.getSum(), 0.001);
  }

  @Test
  public void testAddTransactionStatistic() {

    long time = System.currentTimeMillis() * 1000;
    Transaction transaction = new Transaction(12, time);
    TransactionStatistic statistic = new TransactionStatistic(transaction);
    Assert.assertEquals(1, statistic.getCount());
    Assert.assertEquals(12, statistic.getAvg(), 0.001);
    Assert.assertEquals(12, statistic.getMax(), 0.001);
    Assert.assertEquals(12, statistic.getMin(), 0.001);
    Assert.assertEquals(12, statistic.getSum(), 0.001);
    transaction = new Transaction(4, time);
    statistic.addTransaction(transaction);
    Assert.assertEquals(2, statistic.getCount());
    Assert.assertEquals(8, statistic.getAvg(), 0.001);
    Assert.assertEquals(12, statistic.getMax(), 0.001);
    Assert.assertEquals(4, statistic.getMin(), 0.001);
    Assert.assertEquals(16, statistic.getSum(), 0.001);


    transaction = new Transaction(8, time);
    TransactionStatistic statistic2 = new TransactionStatistic(transaction);

    TimeManager timeManager = Mockito.mock(TimeManager.class);
    Mockito.when(timeManager.getCurrentUTCEpochMilliseconds()).then(invocationOnMock -> time);
    Mockito.when(timeManager.validateTransaction(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Long.class))).then(invocationOnMock -> true);
    statistic.addTransactionStatistic(statistic2, timeManager, time);
    Assert.assertEquals(3, statistic.getCount());
    Assert.assertEquals(8, statistic.getAvg(), 0.001);
    Assert.assertEquals(12, statistic.getMax(), 0.001);
    Assert.assertEquals(4, statistic.getMin(), 0.001);
    Assert.assertEquals(24, statistic.getSum(), 0.001);
  }

}
