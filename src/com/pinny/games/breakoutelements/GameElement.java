package com.pinny.games.breakoutelements;

import java.awt.*;

public abstract class GameElement  {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int movePixels;
    protected int distance;
    protected Color color;
    protected int currentX;
    protected int currentY;

    public GameElement(int x, int y, int width, int height, int movePixels, int distance, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.movePixels = movePixels;
        this.distance = distance;
        this.color = color;
        currentX = x;
        currentY = y;
    }

    public abstract void redraw(Graphics2D g);
    protected abstract void moveElement();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
