package org.rikh.utilities;

/**
 * Constants class to store all values that won't change. Just making changes here will update the entire program for the relevant fields
 * TODO: Need to do for strings properly.
 */
public class Constants {

    //Strings
    public static String opponent = "Computer";
    public static String player = "Player";
    public static String title = "Poker";

    //Constant values
    public static int initialPlayerCoins = 10;
    public static int initialOpponentCoins = 10;
    public static int totalCardsInHand = 5;
    public static int selectableCards = 4;

    //default card width and height
    public static double defaultCardWidth = 50.0;
    public static double defaultCardHeight = 80.0;

    //default coin radius
    public static double tokenRadius = 10.0;
}
