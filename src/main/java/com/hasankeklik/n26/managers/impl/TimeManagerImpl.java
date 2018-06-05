package com.hasankeklik.n26.managers.impl;

import com.hasankeklik.n26.managers.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class TimeManagerImpl implements TimeManager {

  private final int duration;
  private final int interval;

  @Autowired
  public TimeManagerImpl(@Value("${app.duration}") int duration, @Value("${app.duration}") int interval) {
    this.duration = duration;
    this.interval = interval;
  }

  @Override
  public long getCurrentUTCEpochMilliseconds() {
    return OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond() * 1000;
  }

  @Override
  public boolean validateTransaction(long currentTime, long time) {
    return currentTime - time <= duration + interval;
  }

}
