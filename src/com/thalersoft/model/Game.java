package com.thalersoft.model;

public class Game {

    private long startMillis;
    private long pausedMillis;

    private boolean paused;
    private boolean silent;

    private int lastNumDrinksCompleted;
    private int numDrinksGoal;

    private String alertSound;

    public Game(long startMillis, long pausedMillis, boolean paused, boolean silent, int lastNumDrinksCompleted,
                int numDrinksGoal, String alertSound) {
        this.startMillis = startMillis;
        this.pausedMillis = pausedMillis;
        this.paused = paused;
        this.silent = silent;
        this.lastNumDrinksCompleted = lastNumDrinksCompleted;
        this.numDrinksGoal = numDrinksGoal;
        this.alertSound = alertSound;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getPausedMillis() {
        return pausedMillis;
    }

    public void setPausedMillis(long pausedMillis) {
        this.pausedMillis = pausedMillis;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public int getLastNumDrinksCompleted() {
        return lastNumDrinksCompleted;
    }

    public void setLastNumDrinksCompleted(int lastNumDrinksCompleted) {
        this.lastNumDrinksCompleted = lastNumDrinksCompleted;
    }

    public int getNumDrinksGoal() {
        return numDrinksGoal;
    }

    public void setNumDrinksGoal(int numDrinksGoal) {
        this.numDrinksGoal = numDrinksGoal;
    }

    public String getAlertSound() {
        return alertSound;
    }

    public void setAlertSound(String alertSound) {
        this.alertSound = alertSound;
    }

}
