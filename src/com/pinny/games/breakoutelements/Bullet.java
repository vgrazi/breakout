package com.pinny.games.breakoutelements;

import com.pinny.games.Coordinates;

import java.awt.*;
import java.time.LocalDateTime;
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
        g.fillOval(x + Coordinates.getLeftInset(), /*Coordinates.getGameHeight() -*/ (y + Coordinates.getTopInset()), width, height);
        moveElement();

    }

    @Override
    protected void moveElement() {
        // to calculate the new position (currentX, currentY), look at x, y, which is the last position
        // then add the vector distance at the angle, to determine the new vector position, then take cos and sin to
        // calculate the new x and y
        int newX = (int) (x + Math.cos(angle)*movePixels);
        int newY = (int) (y + Math.sin(angle)*movePixels);
        if(newX > Coordinates.getGameWidth() - Coordinates.getRightInset()) {
            // bounce off the right wall
            bounceToLeft();
            newX = Coordinates.getGameWidth() - Coordinates.getRightInset();
        }
        if (newX < Coordinates.getLeftInset()) {
            // bounce off the left wall
            bounceToRight();
            newX = Coordinates.getLeftInset();
        }
        if(newY > Coordinates.getGameHeight() - Coordinates.getBottomInset()) {
            bounceUpOrDown();
            newY = Coordinates.getGameHeight() - Coordinates.getBottomInset();
        }
        if(newY < Coordinates.getTopInset()) {
            bounceUpOrDown();
            newY = Coordinates.getTopInset();
        }
//        else
        {
            x = newX;
            y = newY;
        }
        info(this.toString());
    }

    public void bounceUpOrDown() {
        angle = -angle;
    }

    public void bounceToRight() {
        angle = angle - Math.PI/2;
    }

    public void bounceToLeft() {
        angle = (Math.PI - angle);
    }

    public int getXPos() {
        return x;
    }
    public int getYPos() {
        return y;
    }

    private void info(String string) {
//        System.out.println(LocalDateTime.now() + " " + string);
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "angle=" + (int)Math.round(angle*180/Math.PI) +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public double getAngle() {
        return angle;
    }
}
