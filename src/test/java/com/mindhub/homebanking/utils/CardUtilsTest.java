package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardUtilsTest {

    public static int generateCVV(){
        int cvv = (int)(Math.random() * 999 + 100);
        return cvv;
    }

    public static String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = (int) (Math.random() * 9000 + 1000);
            cardNumber.append(section).append("-");
        }
        return cardNumber.substring(0, cardNumber.length() - 1);
    }

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.generateRandomCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberIsString(){
        String cardNumberString = CardUtils.generateRandomCardNumber();
        assertThat(cardNumberString, isA(String.class));
    }
    @Test
    public void cvvIsLessThousand(){
        int cvvNumber = CardUtils.generateCVV();
        assertThat(cvvNumber, lessThan((999)));
    }

    @Test
    public void cvvIsCreated(){
        int cvvNumberOfDigits = CardUtils.generateCVV();
        assertThat(cvvNumberOfDigits, notNullValue(int.class));
    }
}
