package com.mindhub.homebanking.dto;

public class LoanApplicationDTO {
    private Long id;
    private Double amount;
    private Integer payments;
    private String number;

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getNumber() {
        return number;
    }
}
