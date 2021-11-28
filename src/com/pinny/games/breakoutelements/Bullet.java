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
        int beforeAngle = getAngleInDegrees();
        int newX = (int) (x + Math.cos(angle) * movePixels);
        int newY = (int) (y + Math.sin(angle) * movePixels);
        if (newX < Coordinates.getLeftInset()) {
            // bounce off the left wall
            bounceToRight();
            newX = Coordinates.getLeftInset();
        }
        if (newX > Coordinates.getGameWidth() - Coordinates.getRightInset()) {
            // bounce off the right wall
            bounceToLeft();
            newX = Coordinates.getGameWidth() - Coordinates.getRightInset();
        }
        if (newY > Coordinates.getGameHeight() - Coordinates.getBottomInset()) {
            bounceUpOrDown();
            newY = Coordinates.getGameHeight() - Coordinates.getBottomInset();
        }
        if (newY < Coordinates.getTopInset()) {
            bounceUpOrDown();
            newY = Coordinates.getTopInset();
        }
        int afterAngle = getAngleInDegrees();
        if (beforeAngle != afterAngle)
            System.out.println("Changed " + beforeAngle + " to " + afterAngle);
//        else
        {
            x = newX;
            y = newY;
        }
        info(this.toString());
    }

    private void displayAngle() {
        System.out.println("Angle:" + getAngleInDegrees());
    }

    private int getAngleInDegrees() {
        return (int) (angle / Math.PI * 180);
    }

    public void bounceUpOrDown() {
        angle = -angle;
        adjustAngle();
    }

    public void bounceToRight() {
        angle = angle - Math.PI/2;
    }

    public void bounceToLeft() {
        angle = (Math.PI - angle);
        adjustAngle();
    }

    public void adjustAngle() {
        while (angle > Math.PI * 2) {
            angle -= Math.PI * 2;
        }
        while (angle < 0) {
            angle += Math.PI * 2;
        }
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
                "angle=" + getAngle() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public double getAngle() {
        return angle;
    }
}
