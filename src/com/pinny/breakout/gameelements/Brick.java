package com.pinny.breakout.gameelements;

import java.awt.*;

public class Brick extends GameElement{
    private boolean forward = true;

    public Brick(int x, int y, int width, int height, int movePixels, int distance, Color color) {
        super(x, y, width, height, movePixels, distance, color);
    }

    public void redraw(Graphics2D g) {
        g.setColor(color);
        g.fill3DRect(currentX, y, width, height, true);
        moveElement();
    }

    /**
     * Animates this element one frame
     */
    protected void moveElement() {
        if(forward && currentX < x + distance ) {
            currentX+=movePixels;
        }
        else if(!forward && currentX > x -distance) {
            currentX -= movePixels;
        }
        else{
            forward = !forward;
        }
    }


}
