package com.mindhub.homebanking.models;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.mindhub.homebanking.models.Transaction;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Test
    public void amountMoreThanZero(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("amount" , lessThan(0.1))));
    }

    @Test
    public void description(){
      List<Transaction> transactions = transactionRepository.findAll();
      assertThat(transactions, hasItem(hasProperty("description" , isA(String.class))));
    }
}
