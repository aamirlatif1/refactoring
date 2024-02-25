package com.aamir.model;

import java.util.List;

public class StatementData {
    private final String Customer;

    private final List<PerformanceExt> performances;

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


    public int totalAmount() {
        return performances.stream()
                .mapToInt(PerformanceExt::getAmount).sum();
    }

    public int totalVolumeCredits() {
        return performances.stream()
                .mapToInt(PerformanceExt::getVolumeCreditFor).sum();
    }

}
