package com.mindhub.homebanking.dto;

public class LoanApplicationDTO {
    private Long loanId;
    private Double amount;
    private Integer payments;
    private String number;

    public Long getLoanId() {
        return loanId;
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

    public LoanApplicationDTO(Long loanId, Double amount, Integer payments, String number) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.number = number;
    }
}
