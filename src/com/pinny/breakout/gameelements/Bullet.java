package com.pinny.breakout.gameelements;

import com.pinny.breakout.Coordinates;

import java.awt.*;
import java.util.logging.Logger;

public class Bullet extends GameElement {
    Logger logger = Logger.getLogger("Bullet");

    private double angle = Math.PI / 6;

    public Bullet(int x, int y, int width, int height, int movePixels, int distance, Color color) {
        super(x, y, width, height, movePixels, distance, color);
    }


    @Override
    public void redraw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
        moveElement();

    }

    @Override
    protected void moveElement() {
        // to calculate the new position (currentX, currentY), look at x, y, which is the last position
        // then add the vector distance at the angle, to determine the new vector position, then take cos and sin to
        // calculate the new x and y
        int newX = (int) (x + Math.cos(angle)*movePixels);
        int newY = (int) (y - Math.sin(angle)*movePixels);
        if(newX > Coordinates.getGameWidth()) {
            // bounce off the left wall
            angle = (Math.PI - angle);
        }
        if (newX < 0) {
            angle = angle - Math.PI/2;
        }
        if(newY > Coordinates.getGameHeight()) {
            angle = -angle;
        }
        if(newY < 0) {
            angle = Math.PI - angle;
//            newY = 20;
        }
        x = newX;
        y = newY;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "angle=" + (int)Math.round(angle*180/Math.PI) +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
