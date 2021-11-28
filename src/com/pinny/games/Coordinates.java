package com.pinny.games;

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
    private static int nextBrickXPos = -Coordinates.brickXSpacing;
    private static int nextBrickYPos;
    private static boolean gameWasClicked;
    private static int score;

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
        }
        return nextBrickXPos + getLeftInset();
    }
    public static int getNextYPos() {
        return nextBrickYPos;
    }

    public static boolean gameWasClicked() {
        return gameWasClicked;
    }

    public static void setGameWasClicked() {
        gameWasClicked = true;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Coordinates.score = score;
    }
}
