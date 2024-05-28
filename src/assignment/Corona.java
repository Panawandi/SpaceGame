package assignment;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static assignment.Constants.*;


public class Corona extends GameObject {
    public static final double VMIN = 100, VMAX = 150;      //Asteroids speed
    public Sprite sprite;
    public double rotationPerFrame;


    public Corona(double x, double y, double vx, double vy, Sprite sprite) {
        super(new Vector2D(x, y),
                new Vector2D(vx, vy), sprite.getRadius());
    }

    public Corona() {
        super(new Vector2D(WORLD_WIDTH*Math.random(), WORLD_HEIGHT+Math.random()), new Vector2D(0,0), 0);
        double speed = VMIN+(VMAX-VMIN)*Math.random();
        double angle = Math.random() * 2 * Math.PI;
        vel.set(new Vector2D(speed*Math.cos(angle), speed*Math.sin(angle)));
        deathSound = SoundManager.bangSmall;
        rotationPerFrame = Math.random()*0.1;
        double width = Math.min(Math.max(20+new Random().nextGaussian()*30, 30), 50);

        Image im = Sprite.COVID;
        double height = width * im.getHeight(null)/im.getWidth(null);
        double direction = Math.random() * 2 * Math.PI;
        dir = new Vector2D(Math.cos(direction), Math.sin(direction));
        sprite = new Sprite(im, pos, dir, width, height);
        radius = sprite.getRadius();
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    @Override
    public void update() {
        super.update();
        dir.rotate(rotationPerFrame);
    }

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() != Corona.class;
    }
}
