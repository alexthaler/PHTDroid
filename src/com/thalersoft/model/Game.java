package com.thalersoft.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable{

    private long startMillis;
    private long pausedMillis;

    private boolean stopped;
    private boolean paused;
    private boolean silent;

    private int lastNumDrinksCompleted;
    private int numDrinksGoal;

    private String alertSound;

    public Game() {
    }

    public Game(long startMillis, long pausedMillis, boolean paused, boolean silent, int lastNumDrinksCompleted,
                int numDrinksGoal, String alertSound) {
        this.startMillis = startMillis;
        this.pausedMillis = pausedMillis;
        this.paused = paused;
        this.silent = silent;
        this.lastNumDrinksCompleted = lastNumDrinksCompleted;
        this.numDrinksGoal = numDrinksGoal;
        this.alertSound = alertSound;
        this.stopped = false;
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {

        @Override
        public Game createFromParcel(Parcel parcel) {
            Game game = new Game();
            game.setStartMillis(parcel.readLong());
            game.setPausedMillis(parcel.readLong());
            boolean[] values = new boolean[3];
            parcel.readBooleanArray(values);
            game.setStopped(values[0]);
            game.setPaused(values[1]);
            game.setSilent(values[2]);
            game.setLastNumDrinksCompleted(parcel.readInt());
            game.setNumDrinksGoal(parcel.readInt());
            game.setAlertSound(parcel.readString());
            return game;
        }

        @Override
        public Game[] newArray(int i) {
            throw new UnsupportedOperationException();
        }
    };

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

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(startMillis);
        parcel.writeLong(pausedMillis);
        boolean[] arr = new boolean[3];
        arr[0] = stopped;
        arr[1] = paused;
        arr[2] = silent;
        parcel.writeBooleanArray(arr);
        parcel.writeInt(lastNumDrinksCompleted);
        parcel.writeInt(numDrinksGoal);
        parcel.writeString(alertSound);
    }
}
