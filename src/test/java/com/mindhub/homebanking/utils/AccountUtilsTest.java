package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountUtilsTest {

    @Test
    public void accountNumberIsCreated(){
        String accountNumber = AccountUtils.generateAccountNumber();
        assertThat(accountNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void accountNumberStartsVIN(){
        String startsVIN = AccountUtils.generateAccountNumber();
        assertThat(startsVIN, startsWith("VIN"));
    }
}
