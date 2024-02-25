package com.aamir.invoice;

import com.aamir.model.Performance;
import com.aamir.model.Play;

public class ComedyCalculator extends PerformanceCalculator {

    public ComedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    int amount() {
        var result = 30_000;
        if (getPerformance().getAudience() > 20) {
            result += 10_000 + 500 * (getPerformance().getAudience() - 20);
        }
        result += 300 * getPerformance().getAudience();
        return result;
    }

    @Override
    int volumeCredits() {
        return super.volumeCredits() + (int)Math.floor((float) getPerformance().getAudience() / 5);
    }
}
