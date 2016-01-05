package com.github.qvba;

import java.awt.*;

public class Hud {

    public Hud() {

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

        String highScore = "High Score: " + Dropper.highScore / 1000;
        g.drawString(highScore, 105 - (g.getFontMetrics().stringWidth(highScore)/ 2), 25);

        String currentStage = "Level: " + Dropper.currentStage.getStageID();
        g.drawString(currentStage, 535 - (g.getFontMetrics().stringWidth(currentStage) / 2), 25);

    }
}
