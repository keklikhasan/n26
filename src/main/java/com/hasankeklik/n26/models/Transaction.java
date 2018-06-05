package com.hasankeklik.n26.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

  @JsonProperty("amount")
  private double amount;
  @JsonProperty("timestamp")
  private long timestamp;

  public Transaction() {
  }

  public Transaction(long timestamp) {
    this.timestamp = timestamp;
  }

  public Transaction(double amount, long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return String.format("Transaction[amount:%s, timestamp:%s]", amount, timestamp);
  }

}
