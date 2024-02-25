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
        if (performance.audience() > 20) {
            result += 10_000 + 500 * (performance.audience() - 20);
        }
        result += 300 * performance.audience();
        return result;
    }

    @Override
    int volumeCredits() {
        return super.volumeCredits() + (int) Math.floor((float) performance.audience() / 5);
    }
}
