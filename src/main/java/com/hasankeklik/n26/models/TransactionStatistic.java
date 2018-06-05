package com.hasankeklik.n26.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hasankeklik.n26.managers.TimeManager;

public class TransactionStatistic {

  @JsonProperty("sum")
  private double sum;
  @JsonProperty("avg")
  private double avg;
  @JsonProperty("max")
  private double max = Long.MIN_VALUE;
  @JsonProperty("min")
  private double min = Long.MAX_VALUE;
  @JsonProperty("count")
  private long count;
  @JsonIgnore
  private long timestamp;

  public TransactionStatistic() {
  }

  public TransactionStatistic(Transaction transaction) {
    addTransaction(transaction);
  }

  public double getSum() {
    return sum;
  }

  public void setSum(double sum) {
    this.sum = sum;
  }

  public double getAvg() {
    return avg;
  }

  public void setAvg(double avg) {
    this.avg = avg;
  }

  public double getMax() {
    return max;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public double getMin() {
    return min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void addTransaction(Transaction transaction) {
    if (this.count == 0) {
      this.max = transaction.getAmount();
      this.min = transaction.getAmount();
    }
    this.count++;
    this.sum += transaction.getAmount();
    this.max = Math.max(this.max, transaction.getAmount());
    this.min = Math.min(this.min, transaction.getAmount());
    this.avg = this.sum / this.count;
  }

  public void addTransactionStatistic(TransactionStatistic transactionStatistic, TimeManager timeManager, long currentTime) {
    if (transactionStatistic == null || !transactionStatistic.isValid(timeManager, currentTime)) {
      return;
    }
    this.avg = (transactionStatistic.sum + this.sum) / (transactionStatistic.count + this.count);
    this.sum = transactionStatistic.sum + this.sum;
    this.count = transactionStatistic.count + this.count;
    this.max = Math.max(this.max, transactionStatistic.max);
    this.min = Math.min(this.min, transactionStatistic.min);
  }

  private boolean isValid(TimeManager timeManager, long currentTime) {
    return this.count > 0 && timeManager.validateTransaction(currentTime, this.timestamp);
  }

  @Override
  public String toString() {
    return String.format("Transaction[sum:%s, avg:%s, max:%s, min:%s, count:%s]", sum, avg, max, min, count);
  }
}
