package com.github.qvba;

import com.github.qvba.Location.Area;
import com.github.qvba.Location.Coordinate;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameObject {

    //Location.
    protected int x;
    protected int y;

    //Size
    protected int sizeX;
    protected int sizeY;

    //Color
    protected Color color;

    //Collision
    protected boolean collided;
    protected GameObjectType collidedObject;

    //GameObject type. Ex. PLAYER
    protected GameObjectType type;

    //Velocity.
    protected int velX;
    protected int velY;

    protected final Area screenArea = new Area(new Coordinate(0, 0), new Coordinate(640, 640 / 12 * 9));

    public GameObject(int x, int y, int entityXSize, int entityYSize, Color entityColor, GameObjectType type) {
        this.x = x;
        this.y = y;
        this.sizeX = entityXSize;
        this.sizeY = entityYSize;
        this.color = entityColor;
        this.type = type;
    }

    public abstract void tick();
    public void render(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, sizeX, sizeY);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public GameObjectType getType() {
        return this.type;
    }

    public void setXVelocity(int velX) {
        this.velX = velX;
    }

    public void setYVelocity(int velY) {
        this.velY = velY;
    }

    public int getXVelocity() {
        return this.velX;
    }

    public int getYVelocity() {
        return this.velY;
    }

    public Area getArea() {
        return new Area(new Coordinate(x, y), new Coordinate(x + this.sizeX, y + this.sizeY));
    }

    public Color getColor() {
        return this.color;
    }

    public GameObjectType getCollided() {
        return this.collidedObject;
    }
    public boolean isCollided() {
        return this.collided;
    }
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
    public void setCollidedType(GameObjectType type) {
        this.collidedObject = type;
    }

    //Right Arrow =  39
    //Left Arrow = 37
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}


}
