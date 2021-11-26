package com.pinny.breakout;

import com.pinny.breakout.gameelements.Brick;
import com.pinny.breakout.gameelements.Bullet;
import com.pinny.breakout.gameelements.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Game {
    Logger logger = Logger.getLogger("Game");
    private static final Object MUTEX = new Object();
    private final JFrame frame = new JFrame("Breakout");
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 600;
    private final List<GameElement> gameElements = new ArrayList<>();
    private volatile boolean running;

    public static void main(String[] args) {
        new Game().launch(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void launch(int width, int height) {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel mainPanel = formatFrame(width, height);
        createGameElements();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        running = true;
        executor.submit(()-> {

            while(running) {
                synchronized (MUTEX) {
//                    mainPanel.invalidate();
                    mainPanel.repaint();
                    try {
                        MUTEX.wait(25);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }

    private void createGameElements() {
        addGameElement(new Brick(50, 10, 75, 20, 10, 25, Color.red));
        addGameElement(new Brick(150, 10, 75, 20, 10, 25, Color.red));
        addGameElement(new Bullet(DEFAULT_WIDTH/2, DEFAULT_HEIGHT - 100, 15, 15, 20, 25, Color.white));
    }

    /**
     * Center the frame, color it and display it
     */
    private JPanel formatFrame(int width, int height) {

        JPanel mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                redrawElements((Graphics2D) g);

            }
        };
        mainPanel.setDoubleBuffered(true);
        mainPanel.setBackground(Color.black);
        frame.getContentPane().add(mainPanel);

        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width)/2;
        int y = (screenSize.height - height)/2;
        frame.setLocation(x,y);
        Coordinates.setGameWidth(width);
        Coordinates.setGameHeight(height);
        frame.setVisible(true);
        return mainPanel;
    }

    private void redrawElements(Graphics2D g) {
        gameElements.forEach(element->element.redraw(g));
    }

    public void addGameElement(GameElement e) {
        gameElements.add(e);
    }
}
