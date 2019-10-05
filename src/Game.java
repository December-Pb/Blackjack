import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Author: Jiaqian Sun, Ziqi Tan
*/
public abstract class Game {

    private int gameMode; //1 for Human Dealer - Human Player game; 2 for Human Dealer - Computer Player game; 3 for Computer Dealer - Human Player
    private Deck deck;

    public Game() {
        PlayGame();
    }

    public abstract void PlayGame();

    /**
     * shuffle the cards and initialize the player and dealer
     * @param players
     * @param dealer
     * @param deck
     */
    public abstract void InitlizeGame(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer, Deck deck);

    /**
     * when a round ends, the user can choose continue to play or exit the game, if one of the player has run out of money, the game will exit
     * @param players
     * @param dealer
     * @param input
     * @return
     */
    public abstract boolean showContinueScreen(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer, String input);

    /**
     * Compare who wins in this round, and update the bet
     * @param players
     * @param dealer
     * @param deck
     */
    public abstract void compareDealerandPlayer(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer, Deck deck);

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
