package com.hasankeklik.n26.test.managers;

import com.hasankeklik.n26.managers.TimeManager;
import com.hasankeklik.n26.managers.TransactionManager;
import com.hasankeklik.n26.managers.impl.TransactionManagerImpl;
import com.hasankeklik.n26.models.Transaction;
import com.hasankeklik.n26.models.TransactionStatistic;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class TransactionManagerImplTest {

  @Test
  public void testSaveTransaction() {
    long time = System.currentTimeMillis() * 1000;
    TimeManager timeManager = Mockito.mock(TimeManager.class);
    Mockito.when(timeManager.getCurrentUTCEpochMilliseconds()).then(invocationOnMock -> time);
    Mockito.when(timeManager.validateTransaction(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Long.class))).then(invocationOnMock -> true);

    TransactionManager transactionManager = new TransactionManagerImpl(timeManager, 60000, 1000);
    Transaction transaction = new Transaction(12, time);
    Assert.assertTrue(transactionManager.saveTransaction(transaction));
    transaction = new Transaction(12, time);
    Mockito.when(timeManager.validateTransaction(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Long.class))).then(invocationOnMock -> false);
    Assert.assertFalse(transactionManager.saveTransaction(transaction));
  }

  @Test
  public void testGetTransactionStatistics() {
    long time = System.currentTimeMillis() * 1000;
    TimeManager timeManager = Mockito.mock(TimeManager.class);
    Mockito.when(timeManager.getCurrentUTCEpochMilliseconds()).then(invocationOnMock -> time);
    Mockito.when(timeManager.validateTransaction(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Long.class))).then(invocationOnMock -> true);

    TransactionManager transactionManager = new TransactionManagerImpl(timeManager, 60000, 1000);
    Transaction transaction = new Transaction(12, time);
    Assert.assertTrue(transactionManager.saveTransaction(transaction));
    transaction = new Transaction(4, time);
    Assert.assertTrue(transactionManager.saveTransaction(transaction));

    TransactionStatistic statistic = transactionManager.getTransactionStatistics();
    Assert.assertEquals(2, statistic.getCount());
    Assert.assertEquals(8, statistic.getAvg(), 0.001);
    Assert.assertEquals(12, statistic.getMax(), 0.001);
    Assert.assertEquals(4, statistic.getMin(), 0.001);
    Assert.assertEquals(16, statistic.getSum(), 0.001);
  }

}
