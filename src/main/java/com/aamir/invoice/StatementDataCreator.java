package com.aamir.invoice;

import com.aamir.model.*;

import java.util.Map;
import java.util.stream.Collectors;

record StatementDataCreator(Invoice invoice, Map<String, Play> plays) {

    public StatementData createData() {
        return new StatementData(invoice.customer(),
                invoice.performances().stream().map(this::enrichPerformance)
                        .collect(Collectors.toList()));
    }

    private PerformanceExt enrichPerformance(Performance aPerformance) {
        final var calculator = PerformanceCalculator.createCalculator(aPerformance, playFor(aPerformance));
        final var result = new PerformanceExt(aPerformance);
        result.setPlay(calculator.play);
        result.setAmount(calculator.amount());
        result.setVolumeCreditFor(calculator.volumeCredits());
        return result;
    }

    private Play playFor(Performance perf) {
        return plays.get(perf.playID());
    }
}
