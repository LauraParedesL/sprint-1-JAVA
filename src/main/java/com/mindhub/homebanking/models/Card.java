package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private String number;
    private int cvv;
    private String cardHolder;
    private LocalDate fromDate;
    private LocalDate toDate;
    @Enumerated(EnumType.STRING)
    private CardColor color;
    @ManyToOne
    private Client client;
    private boolean deleteCard = true;
    public Card() {}

    public Card(CardType cardType, String number, int cvv, String cardHolder, LocalDate fromDate, LocalDate toDate, CardColor color) {
        this.cardType = cardType;
        this.number = number;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.color = color;
    }

    public Long getId() {
        return id;
    }


    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isDeleteCard() {
        return deleteCard;
    }

    public void setDeleteCard(boolean deleteCard) {
        this.deleteCard = deleteCard;
    }
}
