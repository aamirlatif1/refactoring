package com.aamir.invoice;

import com.aamir.model.Invoice;
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
            final var format = NumberFormat.getCurrencyInstance(Locale.US);

            for (var perf : invoice.getPerformances()) {
                final var play = plays.get(perf.getPlayID());
                var thisAmount = 0;
                switch (play.getType()) {
                    case "tragedy" -> {
                        thisAmount = 40_000;
                        if (perf.getAudience() > 30) {
                            thisAmount += 1000 * (perf.getAudience() - 30);
                        }
                    }
                    case "comedy" -> {
                        thisAmount = 30_000;
                        if (perf.getAudience() > 20) {
                            thisAmount += 10_000 + 500 * (perf.getAudience() - 20);
                        }
                        thisAmount += 300 * perf.getAudience();
                    }
                    default -> throw new RuntimeException(String.format("unknown type: %s", play.getType()));
                }

                // add volume credits
                volumeCredits += Math.max(perf.getAudience() - 30, 0);
                // add extra credit for every ten comedy attendees
                if ("comedy".equals(play.getType())) {
                    volumeCredits += Math.floor((float) perf.getAudience() / 5);
                }

                // print line for this order
                result += String.format("  %s: %s (%s seats)\n",
                        play.getName(), format.format(thisAmount / 100), perf.getAudience());

                totalAmount += thisAmount;
            }
            result += String.format("Amount owed is %s\n", format.format(totalAmount / 100));
            result += String.format("You earned %s credits\n", volumeCredits);
            return result;
        }

    }

}
