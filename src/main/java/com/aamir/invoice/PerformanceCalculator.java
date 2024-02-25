package com.aamir.invoice;

import com.aamir.model.Performance;
import com.aamir.model.Play;

import static java.lang.Math.max;

public abstract class PerformanceCalculator {

    protected final Performance performance;
    protected final Play play;

    protected PerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public static PerformanceCalculator createCalculator(Performance performance, Play play) {
        return switch (play.type()) {
            case "tragedy" -> new TragedyCalculator(performance, play);
            case "comedy" -> new ComedyCalculator(performance, play);
            default -> throw new RuntimeException(String.format("unknown type: %s", play.type()));
        };
    }

    abstract int amount();

    int volumeCredits() {
        return max(performance.audience() - 30, 0);
    }

}
