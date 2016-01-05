package com.github.qvba.Entity;

import com.github.qvba.Dropper;
import com.github.qvba.GameObject;
import com.github.qvba.GameObjectType;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    private int lastKeyPressed;
    public Player(int x, int y, GameObjectType type) {
        super(x, y, 32, 32, Color.WHITE, type);
    }

    public void tick() {

        //If the player is not trying to go out of bounds, if the player is not then do coordinate += velocity.
        //We use -32 to account for the size of the player as the x coordinate is the left side.
        //We don't need to check Y-Values, since we are never going to allow the player to move across the Y-Axis.
        int tempX = x + velX;
        if(!(tempX > screenArea.getRightBottom().getX() - 32 || tempX < 0)) {
            x = tempX;
        }
        Dropper.gameOver = isCollided();


    }

    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        int keyCode = e.getKeyCode();
        if(keyCode == 39) {
            this.velX = 5;
            lastKeyPressed = 39;
        } else if(keyCode == 37) {
            this.velX = -5;
            lastKeyPressed = 37;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == lastKeyPressed) this.velX = 0;
    }
}
