package com.pinny.games;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Coordinates {
    private static int gameWidth;
    private static int gameHeight;
    private static int leftInset;
    private static int rightInset;
    private static int topInset;
    private static int bottomInset;
    private static boolean paused;
    private static int brickXSpacing = 110;
    private static int brickYSpacing = 50;
    private static int nextBrickXPos = -Coordinates.brickXSpacing - leftInset;
    private static int nextBrickYPos;
    private static boolean pauseOnHit;
    private static int score;
    private static List<Color> colors = Arrays.asList(Color.RED, Color.orange, Color.yellow, Color.GREEN, Color.BLUE);
    private static int[] scores = {500, 300, 150, 100, 50};
    private static Color nextColor = colors.get(0);
    private static int nextIndex = 1;

    public static int getNextScore() {
        return nextScore;
    }

    private static int nextScore = scores[0];

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        Coordinates.paused = paused;
    }

    public static int getGameWidth() {
        return gameWidth;
    }

    public static void setGameWidth(int gameWidth) {
        Coordinates.gameWidth = gameWidth;
    }

    public static int getGameHeight() {
        return gameHeight;
    }

    public static void setGameHeight(int gameHeight) {
        Coordinates.gameHeight = gameHeight;
    }

    public static int getLeftInset() {
        return leftInset;
    }

    public static void setLeftInset(int leftInset) {
        Coordinates.leftInset = leftInset;
        // we can't set the initial nextBrickXPos until the left inset is set
        nextBrickXPos = -Coordinates.brickXSpacing - leftInset;

    }

    public static int getRightInset() {
        return rightInset;
    }

    public static void setRightInset(int rightInset) {
        Coordinates.rightInset = rightInset;
    }

    public static int getTopInset() {
        return topInset;
    }

    public static void setTopInset(int topInset) {
        Coordinates.topInset = topInset;
    }

    public static int getBottomInset() {
        return bottomInset;
    }

    public static void setBottomInset(int bottomInset) {
        Coordinates.bottomInset = bottomInset;
    }

    public static int getNextXPos() {
        Coordinates.nextBrickXPos += Coordinates.brickXSpacing + leftInset;
        if(nextBrickXPos >= gameWidth-brickXSpacing) {
            nextBrickXPos = 0;
            nextBrickYPos += brickYSpacing;
            nextColor = colors.get(nextIndex);
            nextScore = scores[nextIndex];
            nextIndex++;
        }
        return nextBrickXPos + getLeftInset();
    }
    public static int getNextYPos() {
        return nextBrickYPos;
    }

    public static boolean gameWasClicked() {
        return pauseOnHit;
    }

    public static void setPauseOnHit(boolean flag) {
        pauseOnHit = flag;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Coordinates.score = score;
    }

    public static Color getNextColor(){
        return nextColor;
    }

    public static void info(String string) {
        System.out.println(LocalDateTime.now() + " " + string);
    }
}
