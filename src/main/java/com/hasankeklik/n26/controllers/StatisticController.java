package com.hasankeklik.n26.controllers;

import com.hasankeklik.n26.managers.TransactionManager;
import com.hasankeklik.n26.models.TransactionStatistic;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatisticController.class);
  private final TransactionManager transactionManager;

  @Autowired
  public StatisticController(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }


  @ApiOperation(value = "Last 60s statistics", notes = "Returns last 60s transaction statistic", response = TransactionStatistic.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = TransactionStatistic.class)})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity statistics() {
    TransactionStatistic transactionStatistic = transactionManager.getTransactionStatistics();
    LOGGER.info("Response : {}", transactionStatistic);
    return ResponseEntity.status(HttpStatus.OK).body(transactionStatistic);
  }

}
