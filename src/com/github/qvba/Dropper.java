package com.github.qvba;

import com.github.qvba.Entity.Box;
import com.github.qvba.Entity.Handler;
import com.github.qvba.Location.Area;
import com.github.qvba.Location.Coordinate;
import com.github.qvba.Stages.StageHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Dropper extends Canvas implements Runnable{

    public static boolean gameOver = false;
    public static long currentTime;
    public static long highScore;

    private StageHandler stageHandler;

    //Nice 16:9 ratio.
    private final int WIDTH = 640;
    private final int HEIGHT = WIDTH / 12 * 9;
    private final Area screenArea = new Area(new Coordinate(0,0), new Coordinate(WIDTH, HEIGHT));

    private Thread thread;
    private boolean running = false;

    //Misc variables.
    private int FPS = 0;

    private Handler handler;
    private Hud hud;


    public Dropper() {
        System.out.println("Loading Dropper!");
        stageHandler = new StageHandler();
        handler = new Handler(this);
        hud = new Hud(stageHandler);

        new Window(WIDTH, HEIGHT, "Dropper v1.0", this);

        System.out.println("Dropper has started!");
    }

    public static void main(String[] args) {
        if(args != null && args.length > 1) {
            System.out.println("Why are you running this with arguments?");
        }
        new Dropper();
    }


    public synchronized void start() {
        //Initialize and start out thread.
        thread = new Thread(this);
        thread.start();
        running = true;
        System.out.println("Started!");
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }catch(Exception e) {
            System.out.println("Error stopping thread!");
            e.printStackTrace();
        }

    }

    public void run() {

        //Create game loop. Inspired by Minecraft's tick system.
        reset();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        long startTime = timer;
        int frames = 0;
        while(running) {

            //If the player has been hit.
            if(gameOver) {
                handleGameOver();
                startTime = System.currentTimeMillis();
            }

            if(stageHandler.hasRecentlyChanged()) {
                startTime = System.currentTimeMillis();
            }

            //Convert our current time to seconds.
            int currentTimeInt = (int) currentTime / 1000;

            //Stage logic.
            int shouldBeSpeed = stageHandler.getShouldBeSpeed(currentTimeInt);
            int shouldHaveEntities = stageHandler.getEntitiesShouldBeSpawned(currentTimeInt);

            if(handler.getEntityCount() < 1) {
                handler.addObject(new Box());
                handler.setYVelAll(shouldBeSpeed);
            }
            if(handler.getEntityCount() < shouldHaveEntities) {
                handler.spawnEntity(shouldBeSpeed);
            }
            if(handler.getEntityCount() > shouldHaveEntities) {
                handler.clearEntities();
            }

            handler.setYVelAll(stageHandler.getShouldBeSpeed(currentTimeInt));

            long now = System.nanoTime();
            currentTime = System.currentTimeMillis() - startTime;
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1) {
                tick(currentTimeInt);
                delta--;
            }
            if(running) {
                render();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.FPS = frames;
                frames = 0;
            }
        }
        stop();
    }

    private void handleGameOver() {
        reset();
        if(currentTime > highScore) {
            highScore = currentTime;
            stageHandler.died();
        }
        gameOver = false;
    }

    private void reset() {
        handler.clearEntities();
        if(!handler.isPlayerSpawned()) handler.spawnPlayer();
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g);

        g.dispose();

        bs.show();

    }
    private void tick(int currentTime) {
        handler.tick();
        hud.tick();
        stageHandler.tick(currentTime);
    }

    public int getFPS() {
        return this.FPS;
    }

    public Area getScreenArea() {
        return this.screenArea;
    }

}
