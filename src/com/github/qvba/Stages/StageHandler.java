package com.github.qvba.Stages;

public class StageHandler {

    private Stage[] allStages;
    private Stage currentStage;
    private boolean changed;

    public StageHandler() {
        allStages = new Stage[2];
        allStages[0] = new Stage(1, 5, 15, 2, 4);
        allStages[1] = new Stage(2, 10, 15, 2, 5);
        currentStage = allStages[0];
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public int getEntitiesShouldBeSpawned(int timeRunning) {
        return (timeRunning / getSpawnInterval()) + 1;
    }

    public int getShouldBeSpeed(int timeRunning) {
        return 0 - ((timeRunning / getSpeedInterval()) + 1);
    }

    private int getSpawnInterval() {
        return currentStage.getTimeToComplete() / currentStage.getMaxEntities();
    }

    private int getSpeedInterval() {
        return currentStage.getTimeToComplete() / currentStage.getEndingSpeed();
    }

    public void tick(int currentTime) {
        if(currentTime >= currentStage.getTimeToComplete()) {
            if(allStages.length > currentStage.getStageID()) {
                currentStage = allStages[currentStage.getStageID()];
                changed = true;
                return;
            }
        }
        changed = false;
    }

    public boolean hasRecentlyChanged() {
        return changed;
    }

}
