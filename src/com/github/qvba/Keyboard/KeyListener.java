package com.github.qvba.Keyboard;

import com.github.qvba.GameObjects.GameObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyListener extends KeyAdapter {

    LinkedList<GameObject> registeredListeners = new LinkedList<>();

    public void keyPressed(KeyEvent e) {
        for(GameObject object : registeredListeners) {
            object.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        for(GameObject object : registeredListeners) {
            object.keyReleased(e);
        }
    }


    public void addObject(GameObject object) {
        registeredListeners.add(object);
    }

    public void removeObject(GameObject object) {
        registeredListeners.remove(object);
    }
}
