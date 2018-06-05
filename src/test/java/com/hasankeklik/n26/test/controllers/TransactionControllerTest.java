package com.hasankeklik.n26.test.controllers;

import com.hasankeklik.n26.managers.TransactionManager;
import com.hasankeklik.n26.models.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

  @LocalServerPort
  private int port;

  @MockBean
  private TransactionManager transactionManager;

  @Autowired
  private TestRestTemplate restTemplate;


  @Test
  public void testSaveTransactionSuccess() {
    long time = System.currentTimeMillis() * 1000;
    Transaction transaction = new Transaction(12, time);
    Mockito.when(transactionManager.saveTransaction(ArgumentMatchers.any()))
      .thenReturn(true);

    ResponseEntity<String> responseEntity = restTemplate
      .postForEntity("http://localhost:" + port + "transactions", transaction, String.class);
    Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  public void testSaveTransactionFail() {
    long time = System.currentTimeMillis() * 1000;
    Transaction transaction = new Transaction(12, time);
    Mockito.when(transactionManager.saveTransaction(ArgumentMatchers.any()))
      .thenReturn(false);

    ResponseEntity<String> responseEntity = restTemplate
      .postForEntity("http://localhost:" + port + "transactions", transaction, String.class);
    Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }


}
