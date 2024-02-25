package com.aamir.invoice;

import com.aamir.model.*;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class Statement {

    public String statement(Invoice invoice, Map<String, Play> plays) {
        return new Generator(invoice, plays).generator();
    }

    private class Generator {

        private final Invoice invoice;
        private final Map<String, Play> plays;

        public Generator(Invoice invoice, Map<String, Play> plays) {
            this.invoice = invoice;
            this.plays = plays;
        }

        public String generator() {
            var statementData = new StatementData(invoice.getCustomer(), invoice.getPerformances().stream().map(this::enrichPerformance).collect(Collectors.toList()));
            statementData.setTotalAmount(totalAmount(statementData));
            statementData.setTotalVolumeCredits(totalVolumeCredits(statementData));
            return renderPlainText(statementData);
        }

        private PerformanceExt enrichPerformance(Performance aPerformance) {
            var result = new PerformanceExt(aPerformance);
            result.setPlay(playFor(aPerformance));
            result.setAmount(amountFor(result));
            result.setVolumeCreditFor(volumeCreditsFor(result));
            return result;
        }

        private String renderPlainText(StatementData data) {
            var result = String.format("Statement for %s\n", data.getCustomer());
            for (var perf : data.getPerformances()) {
                result += String.format("  %s: %s (%s seats)\n",
                        perf.getPlay().getName(), usd(perf.getAmount()), perf.getAudience());
            }
            result += String.format("Amount owed is %s\n", usd(data.getTotalAmount()));
            result += String.format("You earned %s credits\n", data.getTotalVolumeCredits());
            return result;
        }

        private int totalAmount(StatementData data) {
            var result = 0;
            for (var perf : data.getPerformances()) {
                result += perf.getAmount();
            }
            return result;
        }

        private int totalVolumeCredits(StatementData data) {
            var result = 0;
            for (var perf : data.getPerformances()) {
                result += perf.getVolumeCreditFor();
            }
            return result;
        }

        private String usd(int number) {
            return NumberFormat.getCurrencyInstance(Locale.US).format(number / 100);
        }

        private int volumeCreditsFor(PerformanceExt aPerformance) {
            var result = Math.max(aPerformance.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(aPerformance.getPlay().getType())) {
                result += Math.floor((float) aPerformance.getAudience() / 5);
            }
            return result;
        }

        private int amountFor(PerformanceExt aPerformance) {
            int result;
            switch (aPerformance.getPlay().getType()) {
                case "tragedy" -> {
                    result = 40_000;
                    if (aPerformance.getAudience() > 30) {
                        result += 1000 * (aPerformance.getAudience() - 30);
                    }
                }
                case "comedy" -> {
                    result = 30_000;
                    if (aPerformance.getAudience() > 20) {
                        result += 10_000 + 500 * (aPerformance.getAudience() - 20);
                    }
                    result += 300 * aPerformance.getAudience();
                }
                default ->
                        throw new RuntimeException(String.format("unknown type: %s", aPerformance.getPlay().getType()));
            }
            return result;
        }

        private Play playFor(Performance perf) {
            return plays.get(perf.getPlayID());
        }
    }

}
