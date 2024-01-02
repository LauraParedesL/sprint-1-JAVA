package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)

    private Long id;
    private TransactionType type;
    private LocalDate creationDate;
    private Double amount;
    private String description;
    @ManyToOne
    private Account account;

    public Transaction (){}

    public Transaction(TransactionType type, LocalDate creationDate, Double amount, String description) {
        this.type = type;
        this.creationDate = creationDate;
        this.amount = amount;
        this.description = description;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction(TransactionType type) {
        this.type = type;
    }

}


