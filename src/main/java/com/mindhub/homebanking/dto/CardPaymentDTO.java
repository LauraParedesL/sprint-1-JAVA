package com.mindhub.homebanking.dto;

public class CardPaymentDTO {

    private Long cardPaymentId;
    private String number;
    private int cvv;
    private Double amount;
    private String description;

    public CardPaymentDTO(Long cardPaymentId, String number, int cvv, Double amount, String description) {
        this.cardPaymentId = cardPaymentId;
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    public Long getCardPaymentId() {
        return cardPaymentId;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }


}
