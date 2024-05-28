package assignment;

import utilities.ImageManager;
import utilities.JEasyFrameFull;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static int FRAME_HEIGHT = 800;
    public static int FRAME_WIDTH = 1400;
    public static final int SCORE_PANEL_HEIGHT = 32;
    public static int WORLD_FACTOR = 2;
    public static int WORLD_WIDTH = WORLD_FACTOR * FRAME_WIDTH;
    public static int WORLD_HEIGHT = WORLD_FACTOR * FRAME_HEIGHT;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT+SCORE_PANEL_HEIGHT);
    public static final int FPS = 40;// frames per second
    public static final double DT = 1.0/FPS;  // delay in seconds
    public static Image ASTEROID1, MILKYWAY1;

    static{
        try{
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milky");

        }
        catch (IOException e){
            e.printStackTrace();

        }
    }

    public static void setFullScreenDimensions() {
        FRAME_HEIGHT = JEasyFrameFull.HEIGHT;
        FRAME_WIDTH = JEasyFrameFull.WIDTH;
        WORLD_WIDTH = WORLD_FACTOR * FRAME_WIDTH;
        WORLD_HEIGHT = WORLD_FACTOR * FRAME_HEIGHT;
    }

}
