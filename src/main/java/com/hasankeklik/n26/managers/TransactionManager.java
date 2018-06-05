package com.hasankeklik.n26.managers;

import com.hasankeklik.n26.models.Transaction;
import com.hasankeklik.n26.models.TransactionStatistic;
import org.springframework.stereotype.Component;

@Component
public interface TransactionManager {

  boolean saveTransaction(Transaction transaction);

  TransactionStatistic getTransactionStatistics();

}
