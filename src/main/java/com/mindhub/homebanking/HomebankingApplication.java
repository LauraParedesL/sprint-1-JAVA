package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	//aqui pondremos instrucciones que queremos que se ejecuten cuando la aplicacion arranque

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository ){
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

			Transaction N1 = new Transaction(CREDIT ,  LocalDate.now() , 1.200 , "salary");
			Transaction N2 = new Transaction(CREDIT ,  LocalDate.now() , 2.200 , "Kids");
			Transaction N3 = new Transaction(DEBIT,  LocalDate.now() , -2.400 , "food");
			Transaction N4 = new Transaction(DEBIT ,  LocalDate.now() , -8.50 , "nails");
			VIN001.addTransaction(N1);
			VIN001.addTransaction(N3);
			VIN002.addTransaction(N2);
			VIN002.addTransaction(N4);

			Transaction N5 = new Transaction(CREDIT ,  LocalDate.now() , 4.500 , "cleaning service");
			Transaction N6 = new Transaction(CREDIT ,  LocalDate.now() , 1.200 , "discover good electricity");
			Transaction N7 = new Transaction(DEBIT,  LocalDate.now() , -1.400 , "sickness");
			Transaction N8 = new Transaction(DEBIT ,  LocalDate.now() , -3.500 , "mom");
			VIN003.addTransaction(N5);
			VIN003.addTransaction(N7);
			VIN004.addTransaction(N6);
			VIN004.addTransaction(N8);

			transactionRepository.save(N1);
			transactionRepository.save(N2);
			transactionRepository.save(N3);
			transactionRepository.save(N4);
			transactionRepository.save(N5);
			transactionRepository.save(N6);
			transactionRepository.save(N7);
			transactionRepository.save(N8);



		};
	}
}
