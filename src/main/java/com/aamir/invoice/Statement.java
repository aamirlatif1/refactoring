package com.aamir.invoice;

import com.aamir.model.Invoice;
import com.aamir.model.Performance;
import com.aamir.model.Play;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;


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
            var totalAmount = 0;
            var volumeCredits = 0;


            var result = String.format("Statement for %s\n", invoice.getCustomer());

            for (var perf : invoice.getPerformances()) {
                volumeCredits += volumeCreditsFor( perf);

                // print line for this order
                result += String.format("  %s: %s (%s seats)\n",
                        playFor(perf).getName(), format(amountFor(perf) / 100), perf.getAudience());

                totalAmount += amountFor(perf);
            }
            result += String.format("Amount owed is %s\n", format(totalAmount / 100));
            result += String.format("You earned %s credits\n", volumeCredits);
            return result;
        }

        private String format(int number) {
            return NumberFormat.getCurrencyInstance(Locale.US).format(number);
        }

        private int volumeCreditsFor(Performance aPerformance) {
            var result = Math.max(aPerformance.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(playFor(aPerformance).getType())) {
                result += Math.floor((float) aPerformance.getAudience() / 5);
            }
            return result;
        }

        private Play playFor(Performance perf) {
            return plays.get(perf.getPlayID());
        }

        private int amountFor(Performance aPerformance) {
            int result;
            switch (playFor(aPerformance).getType()) {
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
                default -> throw new RuntimeException(String.format("unknown type: %s", playFor(aPerformance).getType()));
            }
            return result;
        }
    }

}
