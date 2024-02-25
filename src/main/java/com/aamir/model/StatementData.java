package com.aamir.model;

import java.util.List;

public class StatementData {
    private final String customer;
    private final List<PerformanceExt> performances;

    public StatementData(String customer, List<PerformanceExt> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public String customer() {
        return customer;
    }

    public List<PerformanceExt> performances() {
        return performances;
    }

    public int totalAmount() {
        return performances.stream()
                .mapToInt(PerformanceExt::amount).sum();
    }

    public int totalVolumeCredits() {
        return performances.stream()
                .mapToInt(PerformanceExt::volumeCreditFor).sum();
    }

}
