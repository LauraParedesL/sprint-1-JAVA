package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, lastName, email;
    @OneToMany(mappedBy = "client" , fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "client" , fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    @OneToMany(mappedBy = "client" , fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();
    private String password;
    private boolean admin = false;


    public Client() {
    }

    public Client(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void addClientLoan(ClientLoan clientLoan){
       clientLoan.setClient(this);
       this.clientLoans.add(clientLoan);
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);

    }

    public void addCard(Card card){
        card.setClient(this);
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return "Client{" +
                ", id=" + id +
                "name='" + name + '\'' +
                ", lastName=" + lastName +
                ", email=" + email +
                '}';
    }
}
