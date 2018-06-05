package com.hasankeklik.n26.managers;

import org.springframework.stereotype.Component;

@Component
public interface TimeManager {

  long getCurrentUTCEpochMilliseconds();

  boolean validateTransaction(long currentTime, long time);

}
