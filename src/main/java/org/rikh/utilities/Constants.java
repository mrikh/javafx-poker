package org.rikh.utilities;

/**
 * Constants class to store all values that won't change. Just making changes here will update the entire program for the relevant fields
 */
public class Constants {

    //unique identifiers
    public static String quit = "quit";

    //Strings
    public static String kTitle = "Poker";
    public static String kYouWin = "You win cause opponent gave up";
    public static String kYouWinWith = "You win with a ";
    public static String kOpponentWinsWith = "Opponent wins with a ";
    public static String kDraw = "It is a draw!";
    public static String kSecondaryCard = " and secondary card with value of ";
    public static String kAce = "Ace";
    public static String kJack = "Jack";
    public static String kKing = "King";
    public static String kQueen = "Queen";
    public static String kHearts = "Hearts";
    public static String kClubs = "Clubs";
    public static String kDiamonds = "Diamonds";
    public static String kSpades = "Spades";
    public static String kRoyalFlush = "Royal Flush";
    public static String kStraightFlush = "Straight Flush";
    public static String kFourKind = "Four of a Kind";
    public static String kFlush = "Flush";
    public static String kStraight = "Straight";
    public static String kFullHouse = "Full House";
    public static String kTwoPair = "Two Pairs";
    public static String kOnePair = "One Pair";
    public static String kNothing =  "Nothing";
    public static String kTriplets = "Triplets";
    public static String kWithHighestCard = " with highest card ";
    public static String kQuestion = "?";
    public static String kQuit = "Quit";
    public static String kContinue = "Continue";
    public static String kGameOver = "GAME OVER";
    public static String kGoFirst = "Go First";
    public static String kDone = "Done";
    public static String kSelectOnlyFour = "You can only select up to 4 cards";
    public static String kSelectFourToDiscard = "Please select cards to discard by tapping on them";
    public static String kStartBet = "Start Betting";
    public static String kAddTokens = "Add the tokens you want to bet.";
    public static String kChooseBet = "Add a number between 0-3: ";
    public static String kEnterValue = "Enter a value between 0 and 3";
    public static String kEnterNumber = "Input a number";

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

    public static String whiteHex = "#000000";
    public static String redHex = "#ff0000";
    public static String greenHex = "#00ff00";
    public static String blackHex = "#000000";
    public static String lightGreenGex = "#fdd023";
}
