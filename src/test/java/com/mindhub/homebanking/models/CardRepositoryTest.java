package com.mindhub.homebanking.models;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class CardRepositoryTest {
    @Autowired
    private CardRepository cardRepository;


    @Test
    public void isInt(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("cvv" , isA(Integer.class))));
    }
    @Test
    public void cvvNoMoreThanThree(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("cvv" , lessThan(999))));
    }


}
