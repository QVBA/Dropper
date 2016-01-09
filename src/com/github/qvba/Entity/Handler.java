package com.github.qvba.Entity;

import com.github.qvba.Dropper;
import com.github.qvba.GameObjects.GameObject;
import com.github.qvba.GameObjects.GameObjectType;
import com.github.qvba.Keyboard.KeyListener;
import com.github.qvba.Location.Area;

import java.awt.*;
import java.util.LinkedList;

/**
 * Loops through all GameObjects, updates them and renders them to the screen.
 */
public class Handler {

    LinkedList<GameObject> objects = new LinkedList<>();
    private KeyListener keyListener = new KeyListener();

    public Handler(Dropper dropper) {
        keyListener = new KeyListener();
        dropper.addKeyListener(keyListener);
    }

    public void tick() {
        collisionDetection();
        for (GameObject object : objects) {
            object.tick();
        }
    }

    private void collisionDetection() {
        Area[] entityAreas = new Area[objects.size()];
        for(int i = 0; i < objects.size(); i++) {
            entityAreas[i] = objects.get(i).getArea();
        }
        int a = 0;
        for (GameObject object : objects) {
            int b = 0;
            for(Area A : entityAreas) {
                if(object.getArea().areaContains(A)) {
                    if(b != a) {
                        object.setCollided(true);
                        object.setCollidedType(objects.get(b).getType());
                    }else {
                        object.setCollided(false);
                        object.setCollidedType(null);
                    }
                }
                b++;
            }
            a++;
        }
    }

    public void render(Graphics g) {
        for (GameObject object : objects) {
            object.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public GameObject getEntity(int entity) {
        return objects.get(entity);
    }

    public void setYVelAll(int yVel) {
        for(GameObject object : objects) {
            if(object.getType() != GameObjectType.PLAYER) {
                object.setYVelocity(yVel);
            }
        }
    }

    public int getEntityCount() {
        return objects.size() - 1;
    }

    public void clearEntities() {
        for(int i = 1; i < objects.size(); i++) {
            objects.remove(i);
        }
    }

    public boolean isPlayerSpawned() {
        if(objects.size() > 1) {
            for(GameObject object : objects) {
                if(object.getType() == GameObjectType.PLAYER) {
                    return true;
                }
            }
        }
        return false;
    }

    public void spawnPlayer() {
        Player player = new Player(640 / 2-32, (640 / 12 * 9) / 10, GameObjectType.PLAYER);
        addObject(player);
        keyListener.addObject(player);
    }

    public void spawnEntity(int shouldBeSpeed) {
        objects.add(new Box());
        objects.getLast().setYVelocity(shouldBeSpeed);
    }
}
