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
    public void generateStatement() throws Exception {
        // given
        Statement statement = new Statement();
        String expectedStatement =
                "Statement for BigCo\n" +
                "  Hamlet: $650.00 (55 seats)\n" +
                "  As You Like It: $580.00 (35 seats)\n" +
                "  Othello: $500.00 (40 seats)\n" +
                "Amount owed is $1,730.00\n" +
                "You earned 47 credits\n";

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
}
