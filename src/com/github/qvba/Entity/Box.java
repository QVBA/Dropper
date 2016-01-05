package com.github.qvba.Entity;

import com.github.qvba.GameObject;
import com.github.qvba.GameObjectType;

import java.awt.*;
import java.util.Random;

public class Box extends GameObject {

    private int startingY;
    public Box(int x, int y, GameObjectType type) {
        super(x, y, 16, 16, Color.RED, type);
        startingY = y;
        this.velY = -5;
    }

    public Box() {
        super(1000, 1000, 16, 16, Color.RED, GameObjectType.ENTITY); //We set X,Y to arbitary off-screen location so that we can can check the screenarea.
        Random rand = new Random();
        this.x = rand.nextInt(screenArea.getRightBottom().getX());
        this.y = screenArea.getRightBottom().getY();
        this.startingY = y;
        this.velY = 0;
    }


    @Override
    public void tick() {
        y += velY;
        if(y < 0) {
            y = startingY;
            Random rand = new Random();
            x = rand.nextInt(screenArea.getRightBottom().getX());
        }

    }

}
