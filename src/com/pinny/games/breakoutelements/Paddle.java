package com.pinny.games.breakoutelements;

import com.pinny.games.Coordinates;

import java.awt.*;

public class Paddle extends GameElement{
    public Paddle(int x, int y, int width, int height, int movePixels, int distance, Color color){
        super(x, y, width, height, movePixels, distance, color);
    }

    @Override
    public void redraw(Graphics2D g){
        g.setColor(color);
        g.fillArc(x, Coordinates.getGameHeight() - (y + Coordinates.getBottomInset()), width, height, 0, 180);
        moveElement();

    }

    @Override
    protected void moveElement(){

    }

    public Double getHitAngle(Bullet bullet){
        Coordinates.info(String.format("Bullet(%d, %d, %d, %d) Paddle(%d, %d, %d, %d)",
                bullet.x, bullet.y, bullet.width, bullet.height,
                x, y, width, height));
        if(bullet.y + bullet.height < y){
            return null;
        }
        if(bullet.x + bullet.width < x || bullet.x > x + width){
            return null;
        }
        // hitXpos is the x position relative to the paddle left position, of the bullet's left pos
        double hitXPos = bullet.x - x;
        double paddleRadius = width / 2.0;
        double hitYPos = Math.sqrt(paddleRadius * paddleRadius - hitXPos * hitXPos);
        double angle = Math.atan(hitYPos / hitXPos);
        return angle;
    }
}
