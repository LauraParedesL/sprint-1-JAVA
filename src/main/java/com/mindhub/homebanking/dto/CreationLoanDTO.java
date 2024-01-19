package com.mindhub.homebanking.dto;

import java.util.List;

public class CreationLoanDTO {

    private Long loanId;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;
    private Double interest;

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
