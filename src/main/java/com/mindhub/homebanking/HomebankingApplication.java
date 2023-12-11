package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	//aqui pondremos instrucciones que queremos que se ejecuten cuando la aplicacion arranque

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository ){
		return args -> {

			Client melba = new Client("Melba" , "Morel" , "melba@mindhub.com");
			Client nikola = new Client("Nikola" , "Tesla" , "nikolatesla@mindhub.com");
			clientRepository.save(melba);
			clientRepository.save(nikola);
			System.out.println(melba);
			System.out.println(nikola);

			Account VIN001 = new Account("VIN001" , LocalDate.now() , 5.000);
			Account VIN002 = new Account("VIN002" , LocalDate.now().plusDays(1) , 7.500);
			melba.addAccount(VIN001);
			melba.addAccount(VIN002);

			Account VIN003 = new Account("VIN003" , LocalDate.now() , 6.500);
			Account VIN004 = new Account("VIN004" , LocalDate.now() , 8.000);
			nikola.addAccount(VIN003);
			nikola.addAccount(VIN004);

			accountRepository.save(VIN001);
			accountRepository.save(VIN002);
			accountRepository.save(VIN003);
			accountRepository.save(VIN004);


		};
	}
}
