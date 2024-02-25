package com.aamir.invoice;

import com.aamir.model.Performance;
import com.aamir.model.Play;

public class TragedyCalculator extends PerformanceCalculator {

    public TragedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    int amount() {
        var result = 40_000;
        if (getPerformance().getAudience() > 30) {
            result += 1000 * (getPerformance().getAudience() - 30);
        }
        return result;
    }
}
