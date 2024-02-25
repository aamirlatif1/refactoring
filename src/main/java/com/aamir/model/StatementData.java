package com.aamir.model;

import java.util.List;

public record StatementData(String customer, List<PerformanceExt> performances) {

    public int totalAmount() {
        return performances.stream()
                .mapToInt(PerformanceExt::amount).sum();
    }

    public int totalVolumeCredits() {
        return performances.stream()
                .mapToInt(PerformanceExt::volumeCreditFor).sum();
    }

}
