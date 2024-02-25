package com.aamir.invoice;

import com.aamir.model.*;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class Statement {

    public String statement(Invoice invoice, Map<String, Play> plays) {
        return new StatementGenerator(invoice, plays).statement();
    }

    public String htmlStatement(Invoice invoice, Map<String, Play> plays) {
        return new StatementGenerator(invoice, plays).htmlStatement();
    }


    private static class StatementGenerator {

        private final Invoice invoice;
        private final Map<String, Play> plays;

        public StatementGenerator(Invoice invoice, Map<String, Play> plays) {
            this.invoice = invoice;
            this.plays = plays;
        }

        public String statement() {
            return renderPlainText(createStatementData());
        }

        public String htmlStatement() {
            return renderPlainText(createStatementData());
        }

        private String renderHtml(StatementData data) {
            var result = String.format("<h1>Statement for %s</h1>\n", data.getCustomer());
            result += "<table>\n";
            result += "<tr><th>play</th><th>seats</th><th>cost</th></tr>";
            for (var perf : data.getPerformances()) {
                result += String.format("  <tr><td>%s</td><td>%s</td>", perf.getPlay().getName(), perf.getAudience());
                result += String.format("<td>%s</td></tr>\n", usd(perf.getAmount()));
            }
            result += "</table>\n";
            result += String.format("<p>Amount owed is <em>%s</em></p>\n", usd(data.getTotalAmount()));
            result += String.format("<p>You earned <em>%s</em> credits</p>\n", data.getTotalVolumeCredits());
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

        public StatementData createStatementData() {
            final var result = new StatementData(invoice.getCustomer(),
                    invoice.getPerformances().stream().map(this::enrichPerformance)
                            .collect(Collectors.toList()));
            result.setTotalAmount(totalAmount(result));
            result.setTotalVolumeCredits(totalVolumeCredits(result));
            return result;
        }


        private PerformanceExt enrichPerformance(Performance aPerformance) {
            final var calculator = PerformanceCalculator.createCalculator(aPerformance, playFor(aPerformance));
            final var result = new PerformanceExt(aPerformance);
            result.setPlay(calculator.getPlay());
            result.setAmount(calculator.amount());
            result.setVolumeCreditFor(calculator.volumeCredits());
            return result;
        }

        private int totalAmount(StatementData data) {
            return data.getPerformances().stream()
                    .mapToInt(PerformanceExt::getAmount).sum();
        }

        private int totalVolumeCredits(StatementData data) {
            return data.getPerformances().stream()
                    .mapToInt(PerformanceExt::getVolumeCreditFor).sum();
        }

        private String usd(int number) {
            return NumberFormat.getCurrencyInstance(Locale.US).format(number / 100);
        }

        private Play playFor(Performance perf) {
            return plays.get(perf.getPlayID());
        }
    }
}
