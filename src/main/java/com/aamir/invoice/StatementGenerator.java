package com.aamir.invoice;

import com.aamir.model.Invoice;
import com.aamir.model.Play;
import com.aamir.model.StatementData;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;


public class StatementGenerator {

    public String statement(Invoice invoice, Map<String, Play> plays) {
        final var dataCreator = new StatementDataCreator(invoice, plays);
        return renderPlainText(dataCreator.createData());
    }

    public String htmlStatement(Invoice invoice, Map<String, Play> plays) {
        final var dataCreator = new StatementDataCreator(invoice, plays);
        return renderHtml(dataCreator.createData());
    }

    private String renderHtml(StatementData data) {
        var result = new StringBuilder(format("<h1>Statement for %s</h1>\n", data.customer()));
        result.append("<table>\n");
        result.append("<tr><th>play</th><th>seats</th><th>cost</th></tr>\n");
        for (var perf : data.performances()) {
            result.append(format("  <tr><td>%s</td><td>%s</td>", perf.play().name(), perf.audience()));
            result.append(format("<td>%s</td></tr>\n", usd(perf.amount())));
        }
        result.append("</table>\n");
        result.append(format("<p>Amount owed is <em>%s</em></p>\n", usd(data.totalAmount())));
        result.append(format("<p>You earned <em>%s</em> credits</p>\n", data.totalVolumeCredits()));
        return result.toString();
    }

    private String renderPlainText(StatementData data) {
        var result = new StringBuilder(format("Statement for %s\n", data.customer()));
        for (var perf : data.performances()) {
            result.append(format("  %s: %s (%s seats)\n", perf.play().name(), usd(perf.amount()), perf.audience()));
        }
        result.append(format("Amount owed is %s\n", usd(data.totalAmount())));
        result.append(format("You earned %s credits\n", data.totalVolumeCredits()));
        return result.toString();
    }

    private String usd(int number) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(number / 100);
    }

}
