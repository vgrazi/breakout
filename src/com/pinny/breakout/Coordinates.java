package com.pinny.breakout;

public class Coordinates {
    private static int gameWidth;
    private static int gameHeight;

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
}
