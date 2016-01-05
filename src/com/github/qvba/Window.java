package com.github.qvba;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    public Window(int width, int height, String title, Dropper dropper) {

        JFrame frame = new JFrame(title);

        //Set Frame size
        Dimension frameDims = new Dimension(width, height);
        frame.setPreferredSize(frameDims);
        frame.setMaximumSize(frameDims);
        frame.setMinimumSize(frameDims);

        //Set the frames closing operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set some misc things.
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //Add the game to the frame.
        frame.add(dropper);
        frame.setVisible(true);
        dropper.start();
    }
}
