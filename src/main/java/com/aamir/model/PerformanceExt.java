package com.aamir.model;

public class PerformanceExt extends Performance{

    private Play play;
    private int amount;
    private int volumeCreditFor;


    public PerformanceExt(String playID, int audience) {
        super(playID, audience);
    }

    public PerformanceExt(Performance aPerformance) {
        super(aPerformance.getPlayID(), aPerformance.getAudience());
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
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

}
