package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	//aqui pondremos instrucciones que queremos que se ejecuten cuando la aplicacion arranque

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository){
		return args -> {

			Client melba = new Client("Melba" , "Morel" , "melba@mindhub.com");
			Client nikola = new Client("Nikola" , "Tesla" , "nikolatesla@mindhub.com");
			clientRepository.save(melba);
			clientRepository.save(nikola);
			System.out.println(melba);
			System.out.println(nikola);

		};
	}
}
