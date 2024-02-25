package com.aamir.model;

import java.util.List;

public class StatementData {
    private final String Customer;
    private final List<PerformanceExt> performances;

    private int totalAmount;
    private int totalVolumeCredits;

    public StatementData(String customer, List<PerformanceExt> performances) {
        Customer = customer;
        this.performances = performances;
    }

    public String getCustomer() {
        return Customer;
    }

    public List<PerformanceExt> getPerformances() {
        return performances;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalVolumeCredits() {
        return totalVolumeCredits;
    }

    public void setTotalVolumeCredits(int totalVolumeCredits) {
        this.totalVolumeCredits = totalVolumeCredits;
    }

}
