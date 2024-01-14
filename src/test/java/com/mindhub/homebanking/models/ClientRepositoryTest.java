package com.mindhub.homebanking.models;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void clientExists(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }

    @Test
    public void passwordIsString() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("password", instanceOf(String.class))));
    }



}
