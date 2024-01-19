package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private Long loanId;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;
    private Double interest;

    public LoanDTO(Loan loan) {
        this.loanId = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interest = loan.getInterest();
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Double getInterest() {
        return interest;
    }
}
