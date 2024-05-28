package utilities;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class Lib {

    public static int random(int n) {
        return (int) (Math.random() * n);
    }

    public static Color rndColor() {
        return new Color(random(256), random(256), random(256));
    }

    public static float random(float x1, float x2) {
        return x1 + (float) (Math.random() * (x2 - x1));
    }

    public static int sum(Iterable<Integer> xs) {
        int result = 0;
        for (int x : xs)
            result += x;
        return result;
    }

    public static double random(double x1, double x2) {
        return x1 + Math.random() * (x2 - x1);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(Math.max(0, millis));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double timeClosest(Vector2D aPos, Vector2D aVel, Vector2D bPos, Vector2D bVel) {
        Vector2D diffPos = new Vector2D(aPos).subtract(bPos);
        Vector2D diffVel = new Vector2D(aVel).subtract(bVel);
        return -diffPos.dot(diffVel) / diffVel.dot(diffVel);
    }

    public static double dist(Vector2D aPos, Vector2D aVel, Vector2D bPos, Vector2D bVel, double time) {
        Vector2D a = new Vector2D(aPos);
        Vector2D b = new Vector2D(bPos);
        a.addScaled(aVel, time);
        b.addScaled(bVel, time);
        return a.dist(b);
    }

    // create polygon from coordinates (x1,y1,x2,y2,...,xn,yn)
    public static Path2D.Double mkPath2D(double... coords) {
        final int N = coords.length / 2;
        Path2D.Double p = new Path2D.Double();
        if (N < 1)
            throw new IllegalArgumentException("coords array too short");
        // set start point
        p.moveTo(coords[0], coords[1]);
        for (int i = 1; i < N; i++) {
            p.lineTo(coords[i * 2], coords[i * 2 + 1]);
        }
        p.closePath();
        return p;
    }

    // create polygon from coordinates (x1,y1,x2,y2,...,xn,yn)
    public static Polygon mkPolygon(int... coords) {
        int n = coords.length / 2;
        int[] xcoords = new int[n];
        int[] ycoords = new int[n];
        for (int i = 0; i < n; i++) {
            xcoords[i] = coords[2 * i];
            ycoords[i] = coords[2 * i + 1];
        }
        return new Polygon(xcoords, ycoords, n);
    }

    public static Path2D.Float mkRegularPolygon(int n, float radius) {
        Path2D.Float p = new Path2D.Float();
        p.moveTo(radius, 0);
        for (int i = 0; i < n; i++) {
            float x = (float) (Math.cos((Math.PI * 2 * i) / n) * radius);
            float y = (float) (Math.sin((Math.PI * 2 * i) / n) * radius);
            p.lineTo(x, y);
        }
        p.closePath();
        return p;
    }


    public static boolean hasOverlap(Shape s1, Shape s2) {
        Area area = new Area(s1);
        area.intersect(new Area(s2));
        return !area.isEmpty();
    }

}
