package com.aamir.invoice;

import com.aamir.model.Performance;
import com.aamir.model.Play;

public abstract class PerformanceCalculator {

    private final Performance performance;
    private final Play play;

    protected PerformanceCalculator(Performance performance, Play play) {

        this.performance = performance;
        this.play = play;
    }

    public static PerformanceCalculator createCalculator(Performance performance, Play play) {
        return switch (play.getType()) {
            case "tragedy" -> new TragedyCalculator(performance, play);

            case "comedy" -> new ComedyCalculator(performance, play);
            default -> throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        };
    }

    public Performance getPerformance() {
        return performance;
    }

    public Play getPlay() {
        return play;
    }

    abstract int amount();

    int volumeCredits() {
        return Math.max(performance.getAudience() - 30, 0);
    }

}
