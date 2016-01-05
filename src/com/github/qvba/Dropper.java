package com.github.qvba;

import com.github.qvba.Entity.Box;
import com.github.qvba.Entity.Handler;
import com.github.qvba.Location.Area;
import com.github.qvba.Location.Coordinate;
import com.github.qvba.Stages.Stage;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Dropper extends Canvas implements Runnable{

    public static boolean gameOver = false;
    public static long currentTime;
    public static long highScore;
    public static Stage currentStage;

    private Stage[] stageList;

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
        Stage s1 = new Stage(1, 10, 30, 1, 4);
        Stage s2 = new Stage(2, 15, 30, 2, 4);
        stageList = new Stage[2];
        stageList[0] = s1;
        stageList[1] = s2;
        handler = new Handler(this);
        hud = new Hud();

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
        currentStage = stageList[0];
        while(running) {

            //If the player has been hit.
            if(gameOver) {
                handleGameOver(startTime);
                startTime = System.currentTimeMillis();
            }

            //Convert our current time to seconds.
            int currentTimeInt = (int) currentTime / 1000;

            //Handle stage logic.
            //Check to see if we should change stage.
            if(currentStage.getTimeToComplete() <= currentTimeInt) {
                if(stageList.length > currentStage.getStageID()) {
                    reset();
                    startTime = System.currentTimeMillis();
                    currentTimeInt = (int) currentTime / 1000;
                    currentStage = stageList[currentStage.getStageID()];
                }else {
                    //We won the game!
                }
            }
            //Check if we can change any stage variables.
            int currentSpeed = handler.getEntityCount() >= 1 ? handler.getEntity(1).getYVelocity() : 0;
            boolean canSpawn = currentStage.getMaxEntities() > handler.getEntityCount() || handler.getEntityCount() < 1; // -1 to remove the player from entity list.
            boolean canIncreaseSpeed = currentStage.getEndingSpeed() > currentSpeed;
            //Calculate the intervals at which entities should be spawned.
            int spawnInterval = currentStage.getTimeToComplete() / currentStage.getMaxEntities();
            //Check how many entities we should have.
            int amountShouldBeSpawned = handler.getEntityCount() >= 1 ? (currentTimeInt / spawnInterval) + 1 : 1;

            //Calculate the intervals at which speed should be updated.
            int speedInterval = currentStage.getTimeToComplete() / currentStage.getEndingSpeed();
            //Check what the speed should be.
            int shouldBeSpeed = 0 - ((currentTimeInt / speedInterval) + 1);
            if(shouldBeSpeed >= 0) shouldBeSpeed = -1;

            if(canIncreaseSpeed || shouldBeSpeed < currentSpeed) {
                if(handler.getEntityCount() > 1) {
                    handler.setYVelAll(shouldBeSpeed);
                }
            }
            if(canSpawn) {
                if(amountShouldBeSpawned > handler.getEntityCount() || handler.getEntityCount() < 1) {
                    System.out.println("We have spawned an entity with " + shouldBeSpeed);
                    handler.addObject(new Box());
                    handler.setYVelAll(shouldBeSpeed);
                }

            }

            long now = System.nanoTime();
            currentTime = System.currentTimeMillis() - startTime;
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: "+ frames); // DEBUG.
                this.FPS = frames;
                frames = 0;
            }
        }
        stop();

    }

    private void handleGameOver(long startTime) {
        reset();
        if(currentTime > highScore) {
            highScore = currentTime;
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
    private void tick() {
        handler.tick();
        hud.tick();
    }

    public int getFPS() {
        return this.FPS;
    }

    public Area getScreenArea() {
        return this.screenArea;
    }

}
