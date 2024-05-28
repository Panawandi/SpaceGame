package assignment;

import utilities.JEasyFrame;
import utilities.JEasyFrameFull;
import utilities.SoundManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static assignment.Constants.FPS;
import static assignment.Constants.setFullScreenDimensions;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 7;    //Game begins with amount of Asteroids
    public static final int INITIAL_LIVES = 5;      // Lives in Game
    public static final int INITIAL_SAFETY_DURATION = 3000; // millisecs
    boolean shipIsSafe;
    public List<GameObject> objects;
    Ship ship;
    Keys ctrl;
    int score, lives, level, remainingAsteroids;
    String howToPlay;
    View view;
    boolean ended;
    long gameStartTime;  // start time for whole game
    long startTime;  // start time for current level/life
    boolean resetting;

    public Game(boolean fullScreen) {
        if (fullScreen) setFullScreenDimensions();
        SoundManager.play(SoundManager.thrust);
        view = new View(this);
        objects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {

            objects.add(new Corona());

        }
        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
        score = 0;
        remainingAsteroids = N_INITIAL_ASTEROIDS;
        lives = INITIAL_LIVES;
        level = 1;
        ended = false;
        howToPlay = ("                            Esc to quit Game");
        shipIsSafe = true;
        resetting = false;
        JFrame frame = fullScreen ? new JEasyFrameFull(view) : new JEasyFrame(view, "Space Sci-Fi 2D Game assignment, Humanity VS COVID-19");
        frame.setResizable(false);
        frame.addKeyListener(ctrl);
    }

    public static void main(String[] args) {
        Game game = new Game(true);
        game.gameLoop();
    }

    public void gameLoop() {
        long DTMS = Math.round(1000 / FPS); // delay in millisecs
        gameStartTime = startTime = System.currentTimeMillis();
        while (!ended) {
            long time0 = System.currentTimeMillis();
            update();
            view.repaint();
            long timeToSleep = time0 + DTMS - System.currentTimeMillis();
            if (timeToSleep < 0)
                System.out.println("Warning: timeToSleep negative");
            else
                try {
                    Thread.sleep(timeToSleep);
                } catch (Exception e) {
                }


        }

        System.out.println("Game Over");
        System.out.println("Your score was " + score);
        System.out.println("Game time " + (int) ((System.currentTimeMillis() - gameStartTime) / 1000));
    }

    public String howToPlay(){
        return howToPlay;
    }

    public void incScore(int inc) {
        score += inc;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public boolean reset(boolean newlevel) {
        objects.clear();
        if (newlevel) level++;
        else
            lives--;
        if (lives == 0)
            return false;
        for (int i = 0; i < N_INITIAL_ASTEROIDS + (level - 1) * 5; i++) {
            objects.add(new Corona());

        }
        remainingAsteroids = N_INITIAL_ASTEROIDS + (level - 1) * 5;
        ship.reset();
        objects.add(ship);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        shipIsSafe = true;
        startTime = System.currentTimeMillis();
        return true;

    }


    public void update() {

        // suppress collision detection at beginning of game
        if (shipIsSafe) {
            shipIsSafe = System.currentTimeMillis() < startTime + INITIAL_SAFETY_DURATION;
        } else
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    objects.get(i).collisionHandling(objects.get(j));
                }
            }


        ended = true;
        List<GameObject> alive = new ArrayList<>();
        for (GameObject o : objects) {
            if (!o.dead) {
                o.update();
                alive.add(o);
                if (o == ship) ended = false;
            }
            else if (o==ship){
                resetting = true;
                break;
            }
            else updateScore(o);
        }
        if (ship.bullet != null) {
            alive.add(ship.bullet);
            ship.bullet = null;
        }


        synchronized (Game.class) {
            if (remainingAsteroids==0)
                reset(true);
            else if (resetting) {
                ended = !reset(false);
                resetting = false;
            }
            else {
                objects.clear();
                for (GameObject o : alive) objects.add(o);

            }
        }
    }

    public void updateScore(GameObject o) {
        if (o.getClass() == Corona.class) {
            score += 100;
            remainingAsteroids -= 1;
        }
    }

}

