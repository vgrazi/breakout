package com.pinny.games.breakoutelements;

import com.pinny.games.Coordinates;

import java.awt.*;

public class Paddle extends GameElement{
    private int hitXPos;
    private int hitYPos;

    public Paddle(int x, int y, int width, int height, int movePixels, int distance, Color color){
        super(x, y, width, height, movePixels, distance, color);
    }

    @Override
    public void redraw(Graphics2D g){
        g.setColor(color);
        g.fillArc(x, Coordinates.getGameHeight() -y-height, width, height, 0, 180);
        if(hitXPos > 0 && hitYPos > 0)
        {
            g.setColor(Color.red);
            g.drawString(hitXPos + "," + hitYPos, 400, 400);
//            g.drawLine(x, 0, x, Coordinates.getGameHeight());
        }
        moveElement();

    }

    @Override
    protected void moveElement(){

    }

    public Double getHitAngle(Bullet bullet){
        if(bullet.getAngle() <= Math.PI) {
            // heading up
            return null;
        }
        if(bullet.y > y + height){
            return null;
        }
        if(bullet.x + bullet.width < x || bullet.x > x + width){
            return null;
        }
        // hitXpos is the x position relative to the paddle left position, of the bullet's left pos
        double paddleRadius = width / 2.0;
        hitXPos = bullet.x - x;
        hitYPos = (int) Math.sqrt(paddleRadius * paddleRadius - hitXPos * hitXPos);
        double angle = Math.atan(Math.sqrt(paddleRadius * paddleRadius - hitXPos * hitXPos) / (bullet.x - x));
//        Coordinates.info(String.format("Bullet(%d, %d, %d, %d) Paddle(%d, %d, %d, %d)",
//                bullet.x, bullet.y, bullet.width, bullet.height,
//                x, y, width, height));

//        Coordinates.setPaused(true);
        return angle;
    }
}
