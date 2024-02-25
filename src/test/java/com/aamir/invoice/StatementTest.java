package com.aamir.invoice;

import com.aamir.model.Invoice;
import com.aamir.model.Performance;
import com.aamir.model.Play;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementTest {

    @Test
    public void generatePlainTextStatement() {
        // given
        Statement statement = new Statement();
        String expectedStatement = """
                Statement for BigCo
                  Hamlet: $650.00 (55 seats)
                  As You Like It: $580.00 (35 seats)
                  Othello: $500.00 (40 seats)
                Amount owed is $1,730.00
                You earned 47 credits
                """;

        Map<String, Play> plays = Map.of(
                "hamlet", new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy")
        );

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
        ));


        // when
        String actualStatement = statement.statement(invoice, plays);

        // then
        assertEquals(expectedStatement, actualStatement);
    }

    @Test
    public void generateHtmlStatement() {
        // given
        Statement statement = new Statement();
        String expectedStatement = """
                <h1>Statement for BigCo</h1>
                <table>
                <tr><th>play</th><th>seats</th><th>cost</th></tr>
                  <tr><td>Hamlet</td><td>55</td><td>$650.00</td></tr>
                  <tr><td>As You Like It</td><td>35</td><td>$580.00</td></tr>
                  <tr><td>Othello</td><td>40</td><td>$500.00</td></tr>
                </table>
                <p>Amount owed is <em>$1,730.00</em></p>
                <p>You earned <em>47</em> credits</p>
                """;

        Map<String, Play> plays = Map.of(
                "hamlet", new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy")
        );

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
        ));


        // when
        String actualStatement = statement.htmlStatement(invoice, plays);

        // then
        assertEquals(expectedStatement, actualStatement);
    }
}
