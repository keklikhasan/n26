package com.hasankeklik.n26.managers.impl;

import com.hasankeklik.n26.managers.TimeManager;
import com.hasankeklik.n26.managers.TransactionManager;
import com.hasankeklik.n26.models.Transaction;
import com.hasankeklik.n26.models.TransactionStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@Configuration
public class TransactionManagerImpl implements TransactionManager {

  private final TransactionStatistic[] statistics;
  private final Object[] statisticsLocks;
  private final Object statisticsLock = new Object();
  private final TimeManager timeManager;
  private final int duration;
  private final int interval;

  @Autowired
  public TransactionManagerImpl(TimeManager timeManager, @Value("${app.duration}") int duration, @Value("${app.duration}") int interval) {
    this.timeManager = timeManager;
    this.duration = duration;
    this.interval = interval;
    this.statistics = new TransactionStatistic[duration / interval];
    this.statisticsLocks = new Object[this.statistics.length];
    IntStream.range(0, this.statistics.length - 1).forEach(value -> statisticsLocks[value] = new Object());
  }

  @Override
  public boolean saveTransaction(Transaction transaction) {
    long currentTime = timeManager.getCurrentUTCEpochMilliseconds();
    if (!timeManager.validateTransaction(currentTime, transaction.getTimestamp())) {
      return false;
    }
    int index = getTimeIndex(transaction.getTimestamp());
    synchronized (statisticsLocks) {
      TransactionStatistic statistic = new TransactionStatistic(transaction);
      statistic.addTransactionStatistic(statistics[index], timeManager, currentTime);
      statistics[index] = statistic;
    }
    return true;
  }

  public int getTimeIndex(long time) {
    return ((int) time / interval) % statistics.length;
  }

  @Override
  public TransactionStatistic getTransactionStatistics() {
    long currentTime = timeManager.getCurrentUTCEpochMilliseconds();
    TransactionStatistic transactionStatistic = new TransactionStatistic();
    synchronized (statisticsLock) {
      for (TransactionStatistic statistic : statistics) {
        transactionStatistic.addTransactionStatistic(statistic, timeManager, currentTime);
      }
    }
    return transactionStatistic;
  }

}
