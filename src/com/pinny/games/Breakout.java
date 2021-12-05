package com.pinny.games;

import com.pinny.games.breakoutelements.*;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Breakout {
    Logger logger = Logger.getLogger("Game");
    private static final Object MUTEX = new Object();
    private final JFrame frame = new JFrame("Breakout");
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 600;
    private final List<GameElement> gameElements = new ArrayList<>();
    private volatile boolean running;
    private Bullet bullet;
    private Paddle paddle;
    private final int DELAY = 20; // the speed in pixels per this. To make faster, decrease delay


    public static void main(String[] args) {
        new Breakout().launch(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void launch(int width, int height) {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel mainPanel = formatFrame(width, height);
        createGameElements();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        running = true;
        executor.submit(()-> {

            while(running) {
                if (!Coordinates.isPaused()) {
                    synchronized (MUTEX) {
                        mainPanel.repaint();
                        checkForHit();
                        try {
                            MUTEX.wait(DELAY);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });
    }

    private void checkForHit() {
        int x = bullet.getXPos();
        int y = bullet.getYPos();
        double angle = bullet.getAngle();
        gameElements.forEach(gameElement -> {
            if (gameElement instanceof Brick) {
                Brick brick = (Brick) gameElement;
                if (brick.isExploded()) {
                    return;
                }
                HitSide hitSide = brick.getHitSide(x, y, angle);
                if (hitSide == null) {
                    return;
                }
                switch (hitSide) {
                    case bottom:
                    case top:
                        bullet.bounceUpOrDown();
                        break;
                    case left:
                        bullet.bounceToLeft();
                        break;
                    case right:
                        bullet.bounceToRight();
                        break;
                }
                brick.setExploded(true);
                Coordinates.setScore(Coordinates.getScore() + brick.getScore());
                if (Coordinates.gameWasClicked()) {
                    Coordinates.setPaused(true);
                }
            } else if (gameElement instanceof Paddle) {
//                Double hitAngle = ((Paddle) gameElement).getHitAngle(bullet);
//                if (hitAngle != null) {
//                    bullet.setAngle(bullet.getAngle() - hitAngle);
//                    bullet.bounceUpOrDown();
//                    bullet.setAngle(bullet.getAngle() + hitAngle);
//                }
            }
        });
    }

    private void createGameElements() {
        for (int i = 0; i < 35; i++) {
            addGameElement(new Brick(i, Coordinates.getNextXPos() + 20, Coordinates.getNextYPos() + 20, 110, 20, 2, 0, Coordinates.getNextColor(), Coordinates.getNextScore()));
        }
        bullet = new Bullet(DEFAULT_WIDTH -50, Coordinates.getGameHeight() - Coordinates.getBottomInset(), 15, 15, 20, 25, Color.white);
        addGameElement(bullet);
        paddle = new Paddle(DEFAULT_WIDTH / 2, Coordinates.getGameHeight() - Coordinates.getBottomInset(), 60, 60, 20, 25, Color.white);
        addGameElement(paddle);
    }

    /**
     * Center the frame, color it and display it
     */
    private JPanel formatFrame(int width, int height) {

        JPanel mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.yellow);
                g.draw3DRect(Coordinates.getLeftInset(), Coordinates.getTopInset(), Coordinates.getGameWidth(), Coordinates.getGameHeight(), true);
                displayPlayer(g);
                displayScore(g);
                redrawElements((Graphics2D) g);

            }
        };
        mainPanel.setDoubleBuffered(true);
        mainPanel.setBackground(Color.black);
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() == 1) {
                    Coordinates.setPaused(!Coordinates.isPaused());
                    Coordinates.setPauseOnHit(true);
                }
                else {
                    Coordinates.setPauseOnHit(false);
                }
            }
        });
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                paddle.setX(e.getX());
            }
        });
        frame.getContentPane().add(mainPanel);

        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width)/2;
        int y = (screenSize.height - height)/2;
        frame.setLocation(x,y);
        Coordinates.setLeftInset(frame.getInsets().left +10);
        Coordinates.setBottomInset(frame.getInsets().bottom + 20);
        Coordinates.setRightInset(frame.getInsets().right + 10);
        Coordinates.setTopInset(frame.getInsets().top +10);
        Coordinates.setGameWidth(width - Coordinates.getLeftInset() - Coordinates.getRightInset()-30);
        Coordinates.setGameHeight(height - Coordinates.getTopInset() - Coordinates.getBottomInset()-30);
        frame.setVisible(true);
        return mainPanel;
    }

    private void displayPlayer(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("Player:" + "Pinny", Coordinates.getLeftInset() + 10, Coordinates.getGameHeight() - Coordinates.getBottomInset()*3);
    }

    private void displayScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("Score:" + Coordinates.getScore(), Coordinates.getLeftInset() + 10, Coordinates.getGameHeight() - Coordinates.getBottomInset());
    }

    private void redrawElements(Graphics2D g) {
        gameElements.forEach(element->element.redraw(g));
    }

    public void addGameElement(GameElement e) {
        gameElements.add(e);
    }
}
