package com.hasankeklik.n26.controllers;

import com.hasankeklik.n26.managers.TransactionManager;
import com.hasankeklik.n26.models.Transaction;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
  private final TransactionManager transactionManager;

  @Autowired
  public TransactionController(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @ApiOperation(value = "Transactions Handler", notes = "Handle transactions saves transactions")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Success"),
    @ApiResponse(code = 204, message = "If transaction is older than 60 seconds")})
  @PostMapping
  public ResponseEntity transaction(@RequestBody Transaction transaction) {
    LOGGER.info("Request transaction : {}", transaction);
    boolean result = transactionManager.saveTransaction(transaction);
    LOGGER.info("Result : {}", result);
    if (result) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
  }

}
