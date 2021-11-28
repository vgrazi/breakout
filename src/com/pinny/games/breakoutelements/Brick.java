package com.pinny.games.breakoutelements;

import com.pinny.games.Coordinates;

import java.awt.*;

public class Brick extends GameElement{
    private boolean forward = true;
    private boolean exploded;
    private int score;

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Brick(int id, int x, int y, int width, int height, int movePixels, int distance, Color color, int score) {
        super(x, y, width, height, movePixels, distance, color);
        this.score = score;
    }

    public void redraw(Graphics2D g) {
        if(exploded) {
            return;
        }
        g.setColor(color);
        g.fill3DRect(currentX + Coordinates.getLeftInset(), y + Coordinates.getTopInset(), width, height, true);
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

    public int getScore() {
        return score;
    }

    public HitSide getHitSide(int x, int y, double angle) {
        while(angle < 0) {
            angle += Math.PI;
        }
        if(y >= this.y && y <  this.y + height && x >= this.currentX && x<= this.currentX + width) {
            // it's coming from the bottom
            if(angle <= Math.PI) {
                return HitSide.bottom;
            }
            else {
                // it's coming from the top
                return HitSide.top;
            }
        }

        return null;
    }
}
