package com.github.qvba.Stages;

public class Stage {

    protected int stageID;
    protected int maxEntities;
    protected int time;
    protected int startingSpeed;
    protected int endingSpeed;

    public Stage(int stageID, int maxEntities, int time, int startingSpeed, int endingSpeed) {
        this.stageID = stageID;
        this.maxEntities = maxEntities;
        this.startingSpeed = startingSpeed;
        this.endingSpeed = endingSpeed;
        this.time = time;
    }

    public int getStageID() {
        return this.stageID;
    }

    public int getMaxEntities() {
        return this.maxEntities;
    }

    public int getTimeToComplete() {
        return this.time;
    }

    public int getStartingSpeed() {
        return this.startingSpeed;
    }

    public int getEndingSpeed() {
        return this.endingSpeed;
    }
}
