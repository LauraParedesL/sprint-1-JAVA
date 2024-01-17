package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();
    private AccountType accountType;

    private boolean activeAccount = true;
    public Account() {
    }

    public Account(String number, LocalDate creationDate, Double balance, AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }
}

