package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	//aqui pondremos instrucciones que queremos que se ejecuten cuando la aplicacion arranque

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository,
									   AccountRepository accountRepository,
									   TransactionRepository transactionRepository,
									   LoanRepository loanRepository,
									   ClientLoanRepository clientLoanRepository,
									   CardRepository cardRepository){
		return args -> {

			Client melba = new Client("Melba" , "Morel" , "melba@mindhub.com");
			Client nikola = new Client("Nikola" , "Tesla" , "nikolatesla@mindhub.com");
			clientRepository.save(melba);
			clientRepository.save(nikola);
			System.out.println(melba);
			System.out.println(nikola);

			Account VIN001 = new Account("VIN001" , LocalDate.now() , 500.0);
			Account VIN002 = new Account("VIN002" , LocalDate.now().plusDays(1) , 750.0);
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

			Transaction N1 = new Transaction(TransactionType.CREDIT ,  LocalDate.now() , 1.200 , "salary");
			Transaction N2 = new Transaction(TransactionType.CREDIT ,  LocalDate.now() , 2.200 , "Kids");
			Transaction N3 = new Transaction(TransactionType.DEBIT,  LocalDate.now() , -2.400 , "food");
			Transaction N4 = new Transaction(TransactionType.DEBIT ,  LocalDate.now() , -8.50 , "nails");
			VIN001.addTransaction(N1);
			VIN001.addTransaction(N3);
			VIN002.addTransaction(N2);
			VIN002.addTransaction(N4);

			Transaction N5 = new Transaction(TransactionType.CREDIT ,  LocalDate.now() , 4.500 , "cleaning service");
			Transaction N6 = new Transaction(TransactionType.CREDIT ,  LocalDate.now() , 1.200 , "discover good electricity");
			Transaction N7 = new Transaction(TransactionType.DEBIT,  LocalDate.now() , -1.400 , "sickness");
			Transaction N8 = new Transaction(TransactionType.DEBIT ,  LocalDate.now() , -3.500 , "mom");
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

			Loan loan1 = new Loan("mortgage credit", 500.000, List.of(12,24, 36, 48,60));
			Loan loan2 = new Loan("personal credit", 100.000, List.of(6,12,24));
			Loan loan3 = new Loan("automotive credit", 300.000, List.of(6,12,24,36));


			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan melbasMortgage = new ClientLoan(400.000, 60);
			ClientLoan melbasSelfLoan = new ClientLoan(50.000, 12);
			ClientLoan nikolaSelfLoan = new ClientLoan(100.000, 24);
			ClientLoan nikolasAutomotive= new ClientLoan(200.000, 36);

			loan1.addClientLoan(melbasMortgage);
			loan2.addClientLoan(melbasSelfLoan);
			loan2.addClientLoan(nikolaSelfLoan);
			loan3.addClientLoan(nikolasAutomotive);

			melba.addClientLoan(melbasMortgage);
			melba.addClientLoan(melbasSelfLoan);
			nikola.addClientLoan(nikolaSelfLoan);
			nikola.addClientLoan(nikolasAutomotive);

			clientLoanRepository.save(melbasMortgage);
			clientLoanRepository.save(melbasSelfLoan);
			clientLoanRepository.save(nikolaSelfLoan);
			clientLoanRepository.save(nikolasAutomotive);

			Card debitGold = new Card(CardType.DEBIT, "1234-567-890-000", 567, LocalDate.now(), LocalDate.of(2028,12,14), CardColor.GOLD);
			Card creditTitanium = new Card(CardType.CREDIT, "000-567-890-000", 765, LocalDate.now(), LocalDate.now().plusYears(5), CardColor.TITANIUM);

			Card creditSilver = new Card(CardType.CREDIT, "0000-000-000-000", 543, LocalDate.now(),  LocalDate.now().plusYears(5), CardColor.SILVER);

			melba.addCard(debitGold);
			melba.addCard(creditTitanium);
			nikola.addCard(creditSilver);

			cardRepository.save(debitGold);
			cardRepository.save(creditTitanium);
			cardRepository.save(creditSilver);

		};
	}
}
