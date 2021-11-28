package com.pinny.games.breakoutelements;

import com.pinny.games.Coordinates;

import java.awt.*;

public class Brick extends GameElement{
    private boolean forward = true;
    private boolean exploded;

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Brick(int id, int x, int y, int width, int height, int movePixels, int distance, Color color) {
        super(x, y, width, height, movePixels, distance, color);
    }

    public void redraw(Graphics2D g) {
        if (!exploded) {
            g.setColor(color);
            g.fill3DRect(currentX + Coordinates.getLeftInset(), y + Coordinates.getBottomInset(), width, height, true);
//        g.setColor(Color.black);
//        g.setFont(new Font("Arial", Font.BOLD, 20));
//        g.drawString(currentX + "," + y, x + 10, y+height);
            moveElement();
        }
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
