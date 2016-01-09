package com.github.qvba;

import com.github.qvba.Stages.StageHandler;

import java.awt.*;

public class Hud {

    private StageHandler handler;

    public Hud(StageHandler handler) {
        this.handler = handler;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 640, 40);
        g.setColor(Color.GREEN);
        g.setFont(g.getFont().deriveFont(15F));
        String timeRunning = "Current Time: " + Dropper.currentTime / 1000;
        g.drawString(timeRunning, 320 - (g.getFontMetrics().stringWidth(timeRunning) / 2), 25);

        String highScore = "Debug: " + handler.getEntitiesShouldBeSpawned((int) Dropper.currentTime / 1000) + ":" + handler.getShouldBeSpeed((int) Dropper.currentTime / 1000); //DEBUG
        g.drawString(highScore, 105 - (g.getFontMetrics().stringWidth(highScore)/ 2), 25);

        String currentStage = "Level: " + handler.getCurrentStage().getStageID();
        g.drawString(currentStage, 535 - (g.getFontMetrics().stringWidth(currentStage) / 2), 25);

    }
}
