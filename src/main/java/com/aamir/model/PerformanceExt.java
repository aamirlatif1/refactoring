package com.aamir.model;

public class PerformanceExt {

    private Play play;
    private int amount;
    private int volumeCreditFor;

    private final Performance performance;

    public PerformanceExt(Performance aPerformance) {
        this.performance = aPerformance;
    }

    public int amount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int volumeCreditFor() {
        return volumeCreditFor;
    }

    public void setVolumeCreditFor(int volumeCreditFor) {
        this.volumeCreditFor = volumeCreditFor;
    }

    public Play play() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
    
    public int audience() {
        return performance.audience();
    }
}
