package com.hasankeklik.n26.test.controllers;

import com.hasankeklik.n26.managers.TransactionManager;
import com.hasankeklik.n26.models.Transaction;
import com.hasankeklik.n26.models.TransactionStatistic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class StatisticControllerTest {

  @LocalServerPort
  private int port;

  @MockBean
  private TransactionManager transactionManager;

  @Autowired
  private TestRestTemplate restTemplate;


  @Test
  public void testStatistics() {
    long time = System.currentTimeMillis() * 1000;
    Transaction transaction = new Transaction(12, time);
    TransactionStatistic statistic = new TransactionStatistic(transaction);
    Mockito.when(transactionManager.getTransactionStatistics())
      .thenReturn(statistic);

    ResponseEntity<TransactionStatistic> responseEntity = restTemplate
      .getForEntity("http://localhost:" + port + "statistics", TransactionStatistic.class);
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assert.assertNotNull(responseEntity.getBody());
    TransactionStatistic response = responseEntity.getBody();
    Assert.assertEquals(1, response.getCount());
    Assert.assertEquals(12, response.getAvg(), 0.001);
    Assert.assertEquals(12, response.getMax(), 0.001);
    Assert.assertEquals(12, response.getMin(), 0.001);
    Assert.assertEquals(12, response.getSum(), 0.001);
  }


}
