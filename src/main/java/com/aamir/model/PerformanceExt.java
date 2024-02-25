package com.aamir.model;

public class PerformanceExt extends Performance {

    private Play play;
    private int amount;
    private int volumeCreditFor;

    public PerformanceExt(Performance aPerformance) {
        super(aPerformance.getPlayID(), aPerformance.getAudience());
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getVolumeCreditFor() {
        return volumeCreditFor;
    }

    public void setVolumeCreditFor(int volumeCreditFor) {
        this.volumeCreditFor = volumeCreditFor;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
}
