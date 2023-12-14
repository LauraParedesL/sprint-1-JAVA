package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {

    private Long id;
    private CardType cardType;
    private String number;
    private int cvv;
    private LocalDate fromDate, toDate ;
    private String cardHolder;
    private CardColor color;

    public Long getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardColor getColor() {
        return color;
    }

    public CardDTO(Card card){
        id = card.getId();
        cardType = card.getCardType();
        number = card.getNumber();
        cvv = card.getCvv();
        fromDate = card.getFromDate();
        toDate = card.getToDate();
        cardHolder = card.getClient().getName()+' ' + card.getClient().getLastName();
        color = card.getColor();
    }
}
