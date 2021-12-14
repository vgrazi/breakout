package com.pinny.games.breakoutelements;

import com.pinny.games.Coordinates;

import java.awt.*;
import java.util.logging.Logger;

public class Bullet extends GameElement {
    Logger logger = Logger.getLogger("Bullet");

    private double angle = Math.PI / 6;
    private boolean removed;

    public Bullet(int x, int y, int width, int height, int movePixels, int distance, Color color) {
        super(x, y, width, height, movePixels, distance, color);
    }


    @Override
    public void redraw(Graphics2D g) {
        if(removed) {
            return;
        }
        g.setColor(Color.black);
        g.fillRect(45, 315, 100, 40);
        g.setColor(color);
        g.fillOval(x, Coordinates.getGameHeight() - (y), width, height);
        g.drawString(String.valueOf(Coordinates.getAngleInDegrees(angle)), 50, 350);
        if(!Coordinates.isPaused()){
            moveElement();
        }

    }

    int counter = 0;

    @Override
    protected void moveElement() {
        // to calculate the new position (currentX, currentY), look at x, y, which is the last position
        // then add the vector distance at the angle, to determine the new vector position, then take cos and sin to
        // calculate the new x and y
        int beforeAngle = getAngleInDegrees();
        int newX = (int) (x + Math.cos(angle) * movePixels);
        int newY = (int) (y + Math.sin(angle) * movePixels);
        String edge = "";
        if (newX < 0) {
            edge += "left";
            // bounce off the left wall
            bounceToRight();
            newX = 0;
        }
        if (newX > Coordinates.getGameWidth() - width) {
            edge += "right";
            // bounce off the right wall
            bounceToLeft();
            newX = Coordinates.getGameWidth();
        }
        if (newY > Coordinates.getGameHeight()) {
            edge += "top";
            bounceUpOrDown();
            newY = Coordinates.getGameHeight();
        }
        if (newY < 0) {
//            info(this.toString());
            edge += "bottom";
            setRemoved();
        }

        int afterAngle = getAngleInDegrees();
        if (newY < 1) newY = 1;
        x = newX;
        y = newY;
//        info(this.toString());
    }

    private void setRemoved() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    private void displayAngle() {
        System.out.println("Angle:" + getAngleInDegrees());
    }

    private int getAngleInDegrees() {
        return Coordinates.getAngleInDegrees(angle);
    }

    public void bounceUpOrDown() {
        angle = -angle;
        adjustAngle();
    }

    public void bounceToRight() {
        angle = Math.PI - angle;
        adjustAngle();
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
        if(angle == Math.PI) angle -= .1;
        if(angle == 0) angle =.1;
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
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

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
