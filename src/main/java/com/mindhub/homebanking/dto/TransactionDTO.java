package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;
import java.util.List;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private LocalDate creationDate;
    private Double amount;
    private String description;
    private double balance;


    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.creationDate = transaction.getCreationDate();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.balance = transaction.getBalance();
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public double getBalance() {
        return balance;
    }
}
